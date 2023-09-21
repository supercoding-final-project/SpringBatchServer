package com.example.springbatchdemo.domain.repository;

import com.example.springbatchdemo.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {



}
