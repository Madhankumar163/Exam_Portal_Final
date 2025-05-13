package com.cts.service;

import java.util.List;

import com.cts.dto.QuestionDTO;

public interface QuestionService {
	QuestionDTO addQuestion(QuestionDTO dto);

	List<QuestionDTO> getAllQuestions();

	QuestionDTO getQuestionById(Long id);

	void deleteQuestion(Long id);

	List<QuestionDTO> getByCategory(String category);

	List<QuestionDTO> getByDifficulty(String difficulty);
}