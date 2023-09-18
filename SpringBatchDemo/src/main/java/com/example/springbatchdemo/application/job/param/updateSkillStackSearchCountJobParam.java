package com.example.springbatchdemo.application.job.param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
@Getter
public class updateSkillStackSearchCountJobParam {

	private String searchTime;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

//	@Value("#{jobParameters[searchTime]}")
//	private void setCreatedDate(String searchTime) {
//		this.startTime = LocalDateTime.of(LocalDate.parse(createdDate), LocalTime.MAX);
//		this.searchTime = searchTime;
//	}
}
