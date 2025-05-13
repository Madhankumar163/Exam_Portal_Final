package com.cts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.ExamDTO;
import com.cts.entity.Exam;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ExamRepository;

@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamRepository examRepository;

	@Override
	public ExamDTO createExam(ExamDTO dto) {
		Exam exam = new Exam();
		exam.setTitle(dto.getTitle());
		exam.setDescription(dto.getDescription());
		exam.setDuration(dto.getDuration());
		exam.setTotalMarks(dto.getTotalMarks());
		Exam saved = examRepository.save(exam);
		dto.setExamId(saved.getExamId());
		return dto;
	}

	@Override
	public ExamDTO getExamById(Long id) {
		Exam e = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
		ExamDTO dto = new ExamDTO();
		dto.setExamId(e.getExamId());
		dto.setTitle(e.getTitle());
		dto.setDescription(e.getDescription());
		dto.setDuration(e.getDuration());
		dto.setTotalMarks(e.getTotalMarks());
		return dto;
	}

	@Override
	public List<ExamDTO> getAllExams() {
		List<ExamDTO> examDTOList = new ArrayList<>();
		List<Exam> exams = examRepository.findAll(); // Fetch all exams

		for (Exam e : exams) {
			ExamDTO dto = new ExamDTO();
			dto.setExamId(e.getExamId());
			dto.setTitle(e.getTitle());
			dto.setDescription(e.getDescription());
			dto.setDuration(e.getDuration());
			dto.setTotalMarks(e.getTotalMarks());
			examDTOList.add(dto); // Add the transformed object to the list
		}

		return examDTOList; // Return the final list
	}

	@Override
	public ExamDTO updateExam(Long id, ExamDTO dto) {
		Exam e = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
		e.setTitle(dto.getTitle());
		e.setDescription(dto.getDescription());
		e.setDuration(dto.getDuration());
		e.setTotalMarks(dto.getTotalMarks());
		Exam updated = examRepository.save(e);
		dto.setExamId(updated.getExamId());
		return dto;
	}

	@Override
	public void deleteExam(Long id) {

		examRepository.deleteById(id);
	}
}
