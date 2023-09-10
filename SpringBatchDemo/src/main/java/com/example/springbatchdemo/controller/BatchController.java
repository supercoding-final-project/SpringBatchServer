package com.example.springbatchdemo.controller;

import com.example.springbatchdemo.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/batch")
@RestController
public class BatchController {

	private final BatchService batchService;

	@PatchMapping
	public ResponseEntity<Boolean> add(@RequestParam String keyword) {

		batchService.addData(keyword);
		return ResponseEntity.ok(true);
	}

}
