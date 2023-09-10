package com.example.springbatchdemo.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchHistoryModel {

	private String keyword;

	private String searchedAt;

	private Long skillStackId;
}
