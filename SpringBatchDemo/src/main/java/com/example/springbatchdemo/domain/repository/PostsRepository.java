package com.example.springbatchdemo.domain.repository;

import com.example.springbatchdemo.application.model.PostStarSummary;
import com.example.springbatchdemo.domain.entity.Posts;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostsRepository extends JpaRepository<Posts, Long> {

	@Query(
			"SELECT new com.example.springbatchdemo.application.model" +
					".PostStarSummary(p.postId, AVG (r.star)) FROM Posts p " +
					"LEFT JOIN Review r ON p.postId = r.post.postId " +
					"WHERE p.postId IN (" +
					"SELECT DISTINCT pSub.postId FROM Posts pSub " +
					"LEFT JOIN Review rSub ON pSub.postId = rSub.post.postId " +
					"WHERE rSub.createdAt >= :lastJobTime " +
					"AND rSub.createdAt < :jobTime " +
					") " +
					"AND r.isDeleted = false " +
					"AND p.isDeleted = false " +
					"GROUP BY p.postId "
	)
	Page<PostStarSummary> getPostStarSummary(Instant jobTime, Instant lastJobTime, Pageable pageable);
}
