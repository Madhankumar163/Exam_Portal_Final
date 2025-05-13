package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {
	private Long examId;
	private String title;
	private String description;
	private int duration;
	private int totalMarks;
}