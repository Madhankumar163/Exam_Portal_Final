package com.cts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.dto.QuestionDTO;
import com.cts.entity.Question;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.QuestionRepository;
import com.cts.service.QuestionServiceImpl;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

	@Mock
	private QuestionRepository questionRepository;

	@InjectMocks
	private QuestionServiceImpl questionService;

	private Question question;
	private QuestionDTO questionDTO;

	@BeforeEach
	void setUp() {
		question = Question.builder().questionId(1L).text("What is 2+2?").category("Math").difficulty("Easy")
				.correctAnswer("4").build();
		questionDTO = QuestionDTO.builder().questionId(1L).text("What is 2+2?").category("Math").difficulty("Easy")
				.correctAnswer("4").build();
	}

	@Test
	void addQuestion_success() {
		when(questionRepository.save(any(Question.class))).thenReturn(question);
		QuestionDTO result = questionService.addQuestion(questionDTO);
		assertEquals(questionDTO, result);
		verify(questionRepository).save(any(Question.class));
	}

	@Test
	void getQuestionById_success() {
		when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
		QuestionDTO result = questionService.getQuestionById(1L);
		assertEquals(questionDTO, result);
		verify(questionRepository).findById(1L);
	}

	@Test
	void getQuestionById_notFound() {
		when(questionRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> questionService.getQuestionById(1L));
		verify(questionRepository).findById(1L);
	}

	@Test
	void getAllQuestions_success() {
		when(questionRepository.findAll()).thenReturn(Arrays.asList(question));
		var result = questionService.getAllQuestions();
		assertEquals(1, result.size());
		assertEquals(questionDTO, result.get(0));
		verify(questionRepository).findAll();
	}

	@Test
	void deleteQuestion_success() {
		when(questionRepository.existsById(1L)).thenReturn(true);
		questionService.deleteQuestion(1L);
		verify(questionRepository).deleteById(1L);
	}

	@Test
	void getByCategory_success() {
		when(questionRepository.findByCategory("Math")).thenReturn(Arrays.asList(question));
		var result = questionService.getByCategory("Math");
		assertEquals(1, result.size());
		assertEquals(questionDTO, result.get(0));
		verify(questionRepository).findByCategory("Math");
	}
}