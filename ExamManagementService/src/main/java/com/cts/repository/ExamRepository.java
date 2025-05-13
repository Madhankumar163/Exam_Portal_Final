package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
