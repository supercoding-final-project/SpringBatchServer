package com.example.springbatchdemo.application.job;

import com.example.springbatchdemo.application.model.SearchHistoryCountModel;
import com.example.springbatchdemo.domain.entity.SkillStack;
import com.example.springbatchdemo.domain.repository.SearchHistoryRepository;
import com.example.springbatchdemo.domain.repository.SkillStackRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class updateSkillStackSearchCountJobConfig {

	private static final int CHUNK_SIZE = 1000;
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final SearchHistoryRepository searchHistoryRepository;
	private final SkillStackRepository skillStackRepository;


	@Bean
	public Job updateSkillStackSearchCountJob(){
		return jobBuilderFactory.get("updateSkillStackSearchCountJob")
				.incrementer(new RunIdIncrementer())
				.start(updateSkillStackSearchCountStep())
				.build();
	}

	@Bean
	@JobScope
	public Step updateSkillStackSearchCountStep(){
		return stepBuilderFactory.get("updateSkillStackSearchCountStep")
				.<SearchHistoryCountModel, SkillStack>chunk(CHUNK_SIZE)
				.reader(searchHistoryReader())
				.processor(searchHistoryProcessor())
				.writer(searchHistoryWriter())
				.build();
	}

	@Bean
	public RepositoryItemReader<SearchHistoryCountModel> searchHistoryReader(){
		LocalDateTime endTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		LocalDateTime startTime = endTime.minus(1, ChronoUnit.HOURS);
		log.info("startTime : {} endTime : {}", startTime, endTime);

		return new RepositoryItemReaderBuilder<SearchHistoryCountModel>()
				.name("searchHistoryReader")
				.repository(searchHistoryRepository)
				.methodName("find")
				.arguments(startTime, endTime)
				.pageSize(CHUNK_SIZE)
				.sorts(Map.of("id", Sort.Direction.ASC))
				.build();
	}

	public ItemProcessor<SearchHistoryCountModel, SkillStack> searchHistoryProcessor() {
		return searchHistory -> {

			if (searchHistory.getSkillStackId() != null) {
				SkillStack skillStack = skillStackRepository.findById(
						searchHistory.getSkillStackId()).get();

				skillStack.setSkillStackSearchCount(skillStack.getSkillStackSearchCount() + searchHistory.getSearchCount());
				return skillStack;
			}

			return null;
		};
	}

	public RepositoryItemWriter<SkillStack> searchHistoryWriter() {
		return new RepositoryItemWriterBuilder<SkillStack>()
				.repository(skillStackRepository)
				.build();
	}




}
