package com.cts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cts.client.AnalyticsClient;
import com.cts.client.QuestionBankClient;
import com.cts.dto.ExamDTO;
import com.cts.dto.QuestionDTO;
import com.cts.dto.ReportDTO;
import com.cts.dto.ResponseDTO;
import com.cts.entity.Exam;
import com.cts.entity.Response;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ExamRepository;
import com.cts.repository.ResponseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private final ExamRepository examRepository;
	@Autowired
	private final ResponseRepository responseRepository;
	@Autowired
	private final QuestionBankClient questionBankClient;
	@Autowired
	private final AnalyticsClient analyticsClient;

	@Override
	public ExamDTO createExam(ExamDTO examDTO) {
		Exam exam = new Exam();
		exam.setTitle(examDTO.getTitle());
		exam.setDescription(examDTO.getDescription());
		exam.setDuration(examDTO.getDuration());
		exam.setTotalMarks(examDTO.getTotalMarks());
		Exam saved = examRepository.save(exam);
		examDTO.setExamId(saved.getExamId());
		return examDTO;
	}

	@Override
	public ExamDTO getExamById(Long id) {
		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));

		ExamDTO dto = new ExamDTO();
		dto.setExamId(exam.getExamId());
		dto.setTitle(exam.getTitle());
		dto.setDescription(exam.getDescription());
		dto.setDuration(exam.getDuration());
		dto.setTotalMarks(exam.getTotalMarks());

		return dto;
	}

	@Override
	public ExamDTO updateExam(Long id, ExamDTO examDTO) {
		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));

		exam.setTitle(examDTO.getTitle());
		exam.setDescription(examDTO.getDescription());
		exam.setDuration(examDTO.getDuration());
		exam.setTotalMarks(examDTO.getTotalMarks());

		Exam updated = examRepository.save(exam);
		examDTO.setExamId(updated.getExamId());
		return examDTO;
	}

	@Override
	public void deleteExam(Long id) {

		examRepository.deleteById(id);
	}

	@Override
	public ResponseDTO submitResponse(ResponseDTO dto) {
		QuestionDTO question = questionBankClient.getQuestionById(dto.getQuestionId());
		int marks = question.getCorrectAnswer().equals(dto.getAnswer()) ? 1 : 0;

		Response response = new Response();
		response.setExamId(dto.getExamId());
		response.setUserId(dto.getUserId());
		response.setEmailId(dto.getEmailId());
		response.setQuestionId(dto.getQuestionId());
		response.setAnswer(dto.getAnswer());
		response.setMarksObtained(marks);

		Response saved = responseRepository.save(response);
		dto.setResponseId(saved.getResponseId());
		dto.setMarksObtained(marks);
		String subject="exam status";
		String body="exam completed";

		sendEmail(dto.getEmailId(),subject,body);
		// Generate report
		ReportDTO reportDTO = new ReportDTO();
		reportDTO.setExamId(dto.getExamId());
		reportDTO.setUserId(dto.getUserId());
		reportDTO.setTotalMarks(calculateTotalMarks(dto.getUserId(), dto.getExamId()));
		reportDTO.setPerformanceMetrics("Marks: " + reportDTO.getTotalMarks());

		analyticsClient.generateReport(reportDTO);

		return dto;
	}

	@Override
	public List<ResponseDTO> getUserResponses(Long userId, Long examId) {
		List<Response> responses = responseRepository.findByUserIdAndExamId(userId, examId);
		List<ResponseDTO> responseDTOs = new ArrayList<>();

		for (Response response : responses) {
			ResponseDTO dto = new ResponseDTO();
			dto.setResponseId(response.getResponseId());
			dto.setExamId(response.getExamId());
			dto.setUserId(response.getUserId());
			dto.setQuestionId(response.getQuestionId());
			dto.setAnswer(response.getAnswer());
			dto.setMarksObtained(response.getMarksObtained());
			responseDTOs.add(dto);
		}

		return responseDTOs;
	}

	@Override
	public int calculateTotalMarks(Long userId, Long examId) {
		List<Response> responses = responseRepository.findByUserIdAndExamId(userId, examId);
		int totalMarks = 0;

		for (Response response : responses) {
			totalMarks += response.getMarksObtained();
		}

		return totalMarks;

	}



	public String sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);

		javaMailSender.send(message);
		return "final";

	}
}
