package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.ExamDTO;
import com.cts.dto.ResponseDTO;
import com.cts.service.ExamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {

	@Autowired
	private final ExamService examService;

	@PostMapping
	public ExamDTO createExam(@RequestBody ExamDTO examDTO) {
		return examService.createExam(examDTO);
	}

	@GetMapping("/{id}")
	public ExamDTO getExamById(@PathVariable Long id) {
		return examService.getExamById(id);
	}

	@PutMapping("/{id}")
	public ExamDTO updateExam(@PathVariable Long id, @RequestBody ExamDTO examDTO) {
		return examService.updateExam(id, examDTO);
	}

	@DeleteMapping("/{id}")
	public void deleteExam(@PathVariable Long id) {
		examService.deleteExam(id);
	}

	@PostMapping("/submit")
	public ResponseDTO submitResponse(@RequestBody ResponseDTO dto) {
		return examService.submitResponse(dto);
	}

	@GetMapping("/user/{userId}/exam/{examId}/responses")
	public List<ResponseDTO> getUserResponses(@PathVariable Long userId, @PathVariable Long examId) {
		return examService.getUserResponses(userId, examId);
	}

	@GetMapping("/user/{userId}/exam/{examId}/marks")
	public int getTotalMarks(@PathVariable Long userId, @PathVariable Long examId) {
		return examService.calculateTotalMarks(userId, examId);
	}
}
