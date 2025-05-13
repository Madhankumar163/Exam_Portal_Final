package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
	List<Report> findByUserId(Long userId);

	List<Report> findByExamId(Long examId);
}