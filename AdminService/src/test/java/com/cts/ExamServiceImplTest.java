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

import com.cts.dto.ExamDTO;
import com.cts.entity.Exam;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ExamRepository;
import com.cts.service.ExamServiceImpl;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

	@Mock
	private ExamRepository examRepository;

	@InjectMocks
	private ExamServiceImpl examService;

	private Exam exam;
	private ExamDTO examDTO;

	@BeforeEach
	void setUp() {
		exam = new Exam(1L, "Math Exam", "Algebra", 60, 100);
		examDTO = new ExamDTO(1L, "Math Exam", "Algebra", 60, 100);
	}

	@Test
	void createExam_success() {
		when(examRepository.save(any(Exam.class))).thenReturn(exam);
		ExamDTO result = examService.createExam(examDTO);
		assertEquals(examDTO, result);
		verify(examRepository).save(any(Exam.class));
	}

	@Test
	void getExamById_success() {
		when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
		ExamDTO result = examService.getExamById(1L);
		assertEquals(examDTO, result);
		verify(examRepository).findById(1L);
	}

	@Test
	void getExamById_notFound() {
		when(examRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> examService.getExamById(1L));
		verify(examRepository).findById(1L);
	}

	@Test
	void getAllExams_success() {
		when(examRepository.findAll()).thenReturn(Arrays.asList(exam));
		var result = examService.getAllExams();
		assertEquals(1, result.size());
		assertEquals(examDTO, result.get(0));
		verify(examRepository).findAll();
	}

	@Test
	void updateExam_success() {
		when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
		when(examRepository.save(any(Exam.class))).thenReturn(exam);
		ExamDTO result = examService.updateExam(1L, examDTO);
		assertEquals(examDTO, result);
		verify(examRepository).findById(1L);
		verify(examRepository).save(any(Exam.class));
	}

	@Test
	void deleteExam_success() {
		when(examRepository.existsById(1L)).thenReturn(true);
		examService.deleteExam(1L);
		verify(examRepository).deleteById(1L);
	}

	@Test
	void deleteExam_notFound() {
		when(examRepository.existsById(1L)).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> examService.deleteExam(1L));
		verify(examRepository).existsById(1L);
	}
}
