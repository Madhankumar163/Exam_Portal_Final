package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
	private Long responseId;
	private Long examId;
	private Long userId;
	private String emailId;
	private Long questionId;
	private String answer;
	private int marksObtained;
}
