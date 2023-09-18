package com.example.springbatchdemo.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryCountModel {

	private Long skillStackId;

	private Long searchCount;
}
