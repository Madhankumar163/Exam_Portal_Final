package com.cts.service;

import java.util.List;

import com.cts.dto.ExamDTO;

public interface ExamService {
	ExamDTO createExam(ExamDTO examDTO);

	ExamDTO getExamById(Long id);

	List<ExamDTO> getAllExams();

	ExamDTO updateExam(Long id, ExamDTO examDTO);

	void deleteExam(Long id);
}