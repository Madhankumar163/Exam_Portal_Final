package com.cts.service;

import java.util.List;

import com.cts.dto.ReportDTO;

public interface ReportService {
	ReportDTO generateReport(ReportDTO reportDTO);

	List<ReportDTO> getReportsByUser(Long userId);

	List<ReportDTO> getReportsByExam(Long examId);
}
