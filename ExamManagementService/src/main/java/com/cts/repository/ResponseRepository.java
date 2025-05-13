package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
	List<Response> findByUserIdAndExamId(Long userId, Long examId);
}