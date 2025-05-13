package com.cts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.QuestionDTO;
import com.cts.entity.Question;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private final QuestionRepository questionRepo;

	@Override
	public QuestionDTO addQuestion(QuestionDTO dto) {
		Question q = Question.builder().text(dto.getText()).category(dto.getCategory()).difficulty(dto.getDifficulty())
				.correctAnswer(dto.getCorrectAnswer()).build();
		return toDTO(questionRepo.save(q));
	}

	@Override
	public List<QuestionDTO> getAllQuestions() {
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		List<Question> questions = questionRepo.findAll(); // Fetch all questions

		for (Question q : questions) {
			QuestionDTO dto = toDTO(q); // Convert the entity to DTO using the method
			questionDTOList.add(dto); // Add the DTO to the final list
		}

		return questionDTOList; // Return the list
	}

	@Override
	public QuestionDTO getQuestionById(Long id) {
		return toDTO(questionRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id)));
	}

	@Override
	public void deleteQuestion(Long id) {
		if (!questionRepo.existsById(id)) {
			throw new ResourceNotFoundException("Question not found with id: " + id);
		}
		questionRepo.deleteById(id);
	}

	@Override
	public List<QuestionDTO> getByCategory(String category) {
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		List<Question> questions = questionRepo.findByCategory(category); // Fetch questions by category

		for (Question q : questions) {
			QuestionDTO dto = toDTO(q); // Convert entity to DTO
			questionDTOList.add(dto); // Add DTO to list
		}

		return questionDTOList; // Return the final list
	}

	@Override
	public List<QuestionDTO> getByDifficulty(String difficulty) {
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		List<Question> questions = questionRepo.findByDifficulty(difficulty); // Fetch questions by difficulty

		for (Question q : questions) {
			QuestionDTO dto = toDTO(q); // Convert entity to DTO
			questionDTOList.add(dto); // Add DTO to list
		}

		return questionDTOList; // Return the final list
	}

	private QuestionDTO toDTO(Question q) {
		return QuestionDTO.builder().questionId(q.getQuestionId()).text(q.getText()).category(q.getCategory())
				.difficulty(q.getDifficulty()).correctAnswer(q.getCorrectAnswer()).build();
	}

}