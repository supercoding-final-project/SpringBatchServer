package com.example.springbatchdemo.domain.repository;

import com.example.springbatchdemo.application.model.SearchHistoryCountModel;
import com.example.springbatchdemo.domain.entity.SearchHistory;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

	@Query(
			"SELECT new com.example.springbatchdemo.application.model"
					+ ".SearchHistoryCountModel(sh.skillStack.skillStackId, COUNT(sh.skillStack.skillStackId)) "
					+ "FROM SearchHistory sh "
					+ "WHERE sh.searchedAt BETWEEN :startTime AND :endTime "
					+ "GROUP BY sh.skillStack.skillStackId"
	)
	Page<SearchHistoryCountModel> find(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

}
