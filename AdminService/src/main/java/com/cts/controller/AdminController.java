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

import com.cts.client.ExamClient;
import com.cts.client.UserClient;
import com.cts.dto.ExamDTO;
import com.cts.dto.RegisterRequest;
import com.cts.dto.UserDTO;
import com.cts.service.ExamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ExamService examService;
	@Autowired
	private UserClient userClient;
	@Autowired
	private ExamClient examClient;

	@PostMapping("/exams")
	public ExamDTO createExam(@RequestBody ExamDTO examDTO) {
		ExamDTO savedExam = examService.createExam(examDTO);
		examClient.createExam(savedExam);
		return savedExam;
	}

	@GetMapping("/exams/{id}")
	public ExamDTO getExam(@PathVariable Long id) {
		return examService.getExamById(id);
	}

	@GetMapping("/exams")
	public List<ExamDTO> getAllExams() {
		return examService.getAllExams();
	}

	@PutMapping("/exams/{id}")
	public ExamDTO updateExam(@PathVariable Long id, @RequestBody ExamDTO examDTO) {
		ExamDTO updatedExam = examService.updateExam(id, examDTO);
		examClient.updateExam(id, updatedExam);
		return updatedExam;
	}

	@DeleteMapping("/exams/{id}")
	public String deleteExam(@PathVariable Long id) {
		examService.deleteExam(id);
		examClient.deleteExam(id);
		return "Exam deleted";
	}

	@PostMapping("/users/register")
	public UserDTO registerUser(@Valid @RequestBody RegisterRequest request) {
		return userClient.registerUser(request);
	}
}