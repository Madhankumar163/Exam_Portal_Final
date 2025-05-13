package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findByCategory(String category);

	List<Question> findByDifficulty(String difficulty);
}