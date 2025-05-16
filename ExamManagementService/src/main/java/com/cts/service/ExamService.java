package com.cts.service;

import java.util.List;

import com.cts.dto.ExamDTO;
import com.cts.dto.ResponseDTO;

public interface ExamService {
	ExamDTO createExam(ExamDTO examDTO);

	ExamDTO getExamById(Long id);

	ExamDTO updateExam(Long id, ExamDTO examDTO);

	void deleteExam(Long id);

	ResponseDTO submitResponse(ResponseDTO dto);
	
	

	List<ResponseDTO> getUserResponses(Long userId, Long examId);

	int calculateTotalMarks(Long userId, Long examId);
}
