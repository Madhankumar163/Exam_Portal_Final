package com.cts;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.cts.client.AnalyticsClient;
import com.cts.client.QuestionBankClient;
import com.cts.dto.ExamDTO;
import com.cts.dto.QuestionDTO;
import com.cts.dto.ReportDTO;
import com.cts.dto.ResponseDTO;
import com.cts.entity.Exam;
import com.cts.entity.Response;
import com.cts.repository.ExamRepository;
import com.cts.repository.ResponseRepository;
import com.cts.service.ExamServiceImpl;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

	@Mock
	private ExamRepository examRepository;

	@Mock
	private ResponseRepository responseRepository;

	@Mock
	private QuestionBankClient questionBankClient;

	@Mock
	private AnalyticsClient analyticsClient;

	@InjectMocks
	private ExamServiceImpl examService;

	private Exam exam;
	private ExamDTO examDTO;
	private Response response;
	private ResponseDTO responseDTO;
	private QuestionDTO questionDTO;

	@BeforeEach
	void setUp() {
		exam = new Exam(1L, "Math Exam", "Algebra", 60, 100);
		examDTO = new ExamDTO(1L, "Math Exam", "Algebra", 60, 100);
		response = Response.builder().responseId(1L).examId(1L).userId(1L).questionId(1L).answer("4").marksObtained(1)
				.build();
		responseDTO = ResponseDTO.builder().examId(1L).userId(1L).questionId(1L).answer("4").marksObtained(1).build();
		questionDTO = QuestionDTO.builder().questionId(1L).text("What is 2+2?").correctAnswer("4").build();
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
	void submitResponse_success() {
		when(questionBankClient.getQuestionById(1L)).thenReturn(questionDTO);
		when(responseRepository.save(any(Response.class))).thenReturn(response);
		when(analyticsClient.generateReport(any(ReportDTO.class))).thenReturn(new ReportDTO());
		ResponseDTO result = examService.submitResponse(responseDTO);
		assertEquals(1, result.getMarksObtained());
		verify(responseRepository).save(any(Response.class));
		verify(analyticsClient).generateReport(any(ReportDTO.class));
	}

	@Test
	void getUserResponses_success() {
		when(responseRepository.findByUserIdAndExamId(1L, 1L)).thenReturn(Arrays.asList(response));
		var result = examService.getUserResponses(1L, 1L);
		assertEquals(1, result.size());
		assertEquals(responseDTO.getAnswer(), result.get(0).getAnswer());
		verify(responseRepository).findByUserIdAndExamId(1L, 1L);
	}

	@Test
	void calculateTotalMarks_success() {
		when(responseRepository.findByUserIdAndExamId(1L, 1L)).thenReturn(Arrays.asList(response));
		int result = examService.calculateTotalMarks(1L, 1L);
		assertEquals(1, result);
		verify(responseRepository).findByUserIdAndExamId(1L, 1L);
	}
}
