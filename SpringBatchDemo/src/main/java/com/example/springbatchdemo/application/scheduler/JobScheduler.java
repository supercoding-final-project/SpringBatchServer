package com.example.springbatchdemo.application.scheduler;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class JobScheduler {

	private final Job createSearchHistoryJob;
	private final Job updateSkillStackSearchCountJob;
	private final Job updateStarJop;
	private final JobLauncher jobLauncher;

	@Scheduled(cron = "0 35 * * * ?")
	public void runCreateSearchHistoryJob() throws Exception{
		jobLauncher.run(createSearchHistoryJob, new JobParametersBuilder()
				.addDate("date", new Date())
				.toJobParameters());
	}

	@Scheduled(cron = "0 35 * * * ?")
	public void runUpdateSkillStackSearchCountJob() throws Exception{
		jobLauncher.run(updateSkillStackSearchCountJob, new JobParametersBuilder()
				.addDate("date", new Date())
				.toJobParameters());
	}

	@Scheduled(cron = "0 35 * * * ?")
	public void runUpdateStarJop() throws Exception{
		jobLauncher.run(updateStarJop, new JobParametersBuilder()
				.addDate("date", new Date())
				.toJobParameters());
	}
}
