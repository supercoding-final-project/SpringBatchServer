package com.example.springbatchdemo.application.listener;

import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
  private final JobExplorer jobExplorer;
  private final JobRepository jobRepository;

  /**
   * 어떠한 이유 때문에 배치가 멈추거나 에러가 떴을 때
   * 기존에는 batch_job_execution, batch_step_execution STATUS, END_TIME 직접 수정을 해야했습니다.
   * 자동화를 구축하였습니다.
   *
   */
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.info("Stop running jobs.");
    for (String jobName : jobExplorer.getJobNames()) {
      Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(jobName);

      for (JobExecution jobExecution : runningJobExecutions) {
        jobExecution.setStatus(BatchStatus.STOPPED);
        jobExecution.setEndTime(new Date());
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
          if (stepExecution.getStatus().isRunning()) {
            stepExecution.setStatus(BatchStatus.STOPPED);
            stepExecution.setEndTime(new Date());
            jobRepository.update(stepExecution);
          }
        }
        jobRepository.update(jobExecution);
        log.info("Updated job execution status: {}", jobExecution.getJobId());
      }
    }
    log.info("Stopped running jobs.");
  }
}
