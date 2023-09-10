package com.example.springbatchdemo.application.job;

import static com.example.springbatchdemo.service.BatchService.getCSVFilePath;

import com.example.springbatchdemo.application.model.SearchHistoryModel;
import com.example.springbatchdemo.domain.entity.SearchHistory;
import com.example.springbatchdemo.domain.entity.SkillStack;
import com.example.springbatchdemo.domain.repository.SearchHistoryRepository;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CreateSearchHistoryJobConfig {

	private static final int CHUNK_SIZE = 1000;
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final SearchHistoryRepository searchHistoryRepository;
	private final JdbcTemplate demoJdbcTemplate;

	@Bean
	public Job createSearchHistoryJob(){
		return jobBuilderFactory.get("createSearchHistoryJob")
				.incrementer(new RunIdIncrementer())
				.start(createSearchHistoryStep())
				.build();
	}

	@Bean
	@JobScope
	public Step createSearchHistoryStep(){
		return stepBuilderFactory.get("createSearchHistoryStep")
				.<SearchHistoryModel, SearchHistory>chunk(CHUNK_SIZE)
				.reader(createSearchHistoryReader())
				.processor(createSearchHistoryProcessor())
				.writer(createSearchHistoryWriter())
				.build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader<SearchHistoryModel> createSearchHistoryReader(){
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime oneHourBefore = currentDateTime.minusHours(1);
		String csvFilePath = getCSVFilePath(oneHourBefore);
		Resource resource = new FileSystemResource(csvFilePath);

		return new FlatFileItemReaderBuilder<SearchHistoryModel>()
				.name("createSearchHistoryReader")
				.resource(resource)
				.delimited()
				.names("keyword", "searchedAt", "skillStackId")
				.fieldSetMapper(new BeanWrapperFieldSetMapper<>())
				.targetType(SearchHistoryModel.class)
				.build();
	}

	public ItemProcessor<SearchHistoryModel, SearchHistory> createSearchHistoryProcessor(){
		log.info("!!!!!!");

		return searchHistoryModel -> {
			String dateTimeString = searchHistoryModel.getSearchedAt();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

			if (searchHistoryModel.getSkillStackId() == -1) {
				return SearchHistory.builder()
						.keyword(searchHistoryModel.getKeyword())
						.skillStack(null)
						.searchedAt(dateTime)
						.build();
			} else {
				return SearchHistory.builder()
						.keyword(searchHistoryModel.getKeyword())
						.skillStack(
								SkillStack.builder()
										.skillStackId(searchHistoryModel.getSkillStackId())
										.build()
						)
						.searchedAt(dateTime)
						.build();
			}

		};
	}
// 2023-09-09 07:14:28.110  INFO 18248 --- [  restartedMain] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=createSearchHistoryJob]] completed with the following parameters: [{run.id=1}] and the following status: [COMPLETED] in 25s887ms
	// 10만개 데이터 생성 -> 25sec
//	public RepositoryItemWriter<SearchHistory> createSearchHistoryWriter(){
//		return new RepositoryItemWriterBuilder<SearchHistory>()
//				.repository(searchHistoryRepository)
//				.build();
//	}

	// 성능 최적화
//	2023-09-09 07:17:25.065  INFO 18676 --- [  restartedMain] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=createSearchHistoryJob]] completed with the following parameters: [{run.id=2}] and the following status: [COMPLETED] in 4s416ms
	// 10만개 데이터 생성 -> 4sec

	public ItemWriter<SearchHistory> createSearchHistoryWriter() {
		return searchHistories -> demoJdbcTemplate.batchUpdate(
				"insert into search_history (keyword, skill_stack_id, searchedAt) values (?, ?, ?)",
				searchHistories,
				CHUNK_SIZE,
				(ps, searchHistory) -> {
					ps.setObject(1, searchHistory.getKeyword());
					if (searchHistory.getSkillStack() != null) {
						ps.setObject(2, searchHistory.getSkillStack().getSkillStackId());
					} else {
						ps.setNull(2, Types.INTEGER);
					}
					ps.setObject(3, searchHistory.getSearchedAt());
				});
	}
}
