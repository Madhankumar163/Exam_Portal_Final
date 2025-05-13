package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {
	private Long reportId;
	private Long examId;
	private Long userId;
	private Integer totalMarks;
	private String performanceMetrics;
}