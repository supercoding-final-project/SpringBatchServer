package com.example.springbatchdemo.application.job;

import com.example.springbatchdemo.application.model.PostStarSummary;
import com.example.springbatchdemo.domain.entity.Posts;
import com.example.springbatchdemo.domain.repository.PostsRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
public class UpdateStarJopConfig {

	private static final int CHUNK_SIZE = 1000;
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final PostsRepository postsRepository;

	@Bean
	public Job updateStarJop(){
		return jobBuilderFactory.get("updateStarJop")
				.incrementer(new RunIdIncrementer())
				.start(updatePostStarStep())
				.build();
	}

	@Bean
	@JobScope
	public Step updatePostStarStep(){
		return stepBuilderFactory.get("UpdatePostStarStep")
				.<PostStarSummary, Posts>chunk(CHUNK_SIZE)
				.reader(postStarSummaryReader())
				.processor(postStarSummaryProcessor())
				.writer(postStarSummaryWriter())
				.build();
	}

	@Bean
	public RepositoryItemReader<PostStarSummary> postStarSummaryReader(){
		LocalDateTime jobTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		LocalDateTime lastJobTime = jobTime.minus(1, ChronoUnit.HOURS);

		Instant jobTimeInstant = jobTime.atZone(ZoneOffset.systemDefault()).toInstant();
		Instant lastJobTimeInstant = lastJobTime.atZone(ZoneOffset.systemDefault()).toInstant();

		return new RepositoryItemReaderBuilder<PostStarSummary>()
				.name("postStarSummaryReader")
				.repository(postsRepository)
				.methodName("getPostStarSummary")
				.arguments(jobTimeInstant, lastJobTimeInstant)
				.pageSize(CHUNK_SIZE)
				.sorts(Map.of("id", Sort.Direction.ASC))
				.build();
	}

	public ItemProcessor<PostStarSummary, Posts> postStarSummaryProcessor() {
		log.info("!!!");
		return postStarSummary -> {

			if (postStarSummary.getPostId() != null) {
				Posts posts = postsRepository.findById(
						postStarSummary.getPostId()).get();

				Double starAvg = postStarSummary.getStarAvg();
				Float updateStar = Math.round(starAvg * 10) / 10f;
				posts.updateStar(updateStar);
				return posts;
			}

			return null;
		};
	}

	public RepositoryItemWriter<Posts> postStarSummaryWriter() {
		return new RepositoryItemWriterBuilder<Posts>()
				.repository(postsRepository)
				.build();
	}
}
