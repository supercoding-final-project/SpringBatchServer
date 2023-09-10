package com.example.springbatchdemo.domain.repository;

import com.example.springbatchdemo.domain.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

}
