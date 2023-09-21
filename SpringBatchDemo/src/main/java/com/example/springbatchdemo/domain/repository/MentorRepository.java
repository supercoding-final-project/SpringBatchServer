package com.example.springbatchdemo.domain.repository;

import com.example.springbatchdemo.domain.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

}
