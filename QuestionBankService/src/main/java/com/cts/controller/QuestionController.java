package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.QuestionDTO;
import com.cts.service.QuestionService;
import com.netflix.discovery.converters.Auto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

	@Autowired
	private final QuestionService questionService;

	@PostMapping("/add")
	public QuestionDTO addQuestion(@RequestBody QuestionDTO dto) {
		return questionService.addQuestion(dto);
	}

	@GetMapping("/all")
	public List<QuestionDTO> getAllQuestions() {
		return questionService.getAllQuestions();
	}

	@GetMapping("/{id}")
	public QuestionDTO getQuestionById(@PathVariable Long id) {
		return questionService.getQuestionById(id);
	}

	@DeleteMapping("/{id}")
	public String deleteQuestion(@PathVariable Long id) {
		questionService.deleteQuestion(id);
		return "Question Deleted";
	}

	@GetMapping("/category/{category}")
	public List<QuestionDTO> getByCategory(@PathVariable String category) {
		return questionService.getByCategory(category);
	}

	@GetMapping("/difficulty/{level}")
	public List<QuestionDTO> getByDifficulty(@PathVariable String level) {
		return questionService.getByDifficulty(level);
	}
}