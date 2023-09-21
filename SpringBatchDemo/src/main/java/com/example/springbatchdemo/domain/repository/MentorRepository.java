package com.example.springbatchdemo.domain.repository;

import com.example.springbatchdemo.application.model.MentorStarSummary;
import com.example.springbatchdemo.domain.entity.Mentor;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

	@Query(
			"SELECT new com.example.springbatchdemo.application.model" +
					".MentorStarSummary(m.mentorId, AVG (p.star)) FROM Mentor m " +
					"LEFT JOIN Posts p ON m.mentorId = p.mentor.mentorId " +
					"WHERE m.mentorId IN (" +
					"SELECT DISTINCT mSub.mentorId FROM Mentor mSub " +
					"LEFT JOIN Posts pSub ON mSub.mentorId = pSub.mentor.mentorId " +
					"WHERE pSub.updatedAt >= :jobTime " +
					") " +
					"AND p.isDeleted = false " +
					"GROUP BY m.mentorId "
	)
	Page<MentorStarSummary> getMentorStarSummary(Instant jobTime, Pageable pageable);
}
