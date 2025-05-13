package com.cts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportId;
	private Long examId;
	private Long userId;
	private Integer totalMarks;
	private String performanceMetrics;
}