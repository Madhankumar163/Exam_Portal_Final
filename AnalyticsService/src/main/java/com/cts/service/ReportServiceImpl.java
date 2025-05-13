package com.cts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.dto.ReportDTO;
import com.cts.entity.Report;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final ReportRepository reportRepository;

	@Override
	public ReportDTO generateReport(ReportDTO reportDTO) {
		Report report = new Report();
		report.setExamId(reportDTO.getExamId());
		report.setUserId(reportDTO.getUserId());
		report.setTotalMarks(reportDTO.getTotalMarks());
		report.setPerformanceMetrics(reportDTO.getPerformanceMetrics());

		Report saved = reportRepository.save(report);
		reportDTO.setReportId(saved.getReportId());
		return reportDTO;
	}

	@Override
	public List<ReportDTO> getReportsByUser(Long userId) {
		List<Report> reports = reportRepository.findByUserId(userId);
		if (reports.isEmpty()) {
			throw new ResourceNotFoundException("No reports found for user with id: " + userId);
		}

		List<ReportDTO> reportDTOs = new ArrayList<>();
		for (int i = 0; i < reports.size(); i++) {
			Report report = reports.get(i);
			ReportDTO dto = new ReportDTO();
			dto.setReportId(report.getReportId());
			dto.setExamId(report.getExamId());
			dto.setUserId(report.getUserId());
			dto.setTotalMarks(report.getTotalMarks());
			dto.setPerformanceMetrics(report.getPerformanceMetrics());
			reportDTOs.add(dto);
		}
		return reportDTOs;
	}

	@Override
	public List<ReportDTO> getReportsByExam(Long examId) {
		List<Report> reports = reportRepository.findByExamId(examId);
		if (reports.isEmpty()) {
			throw new ResourceNotFoundException("No reports found for exam with id: " + examId);
		}

		List<ReportDTO> reportDTOs = new ArrayList<>();
		for (int i = 0; i < reports.size(); i++) {
			Report report = reports.get(i);
			ReportDTO dto = new ReportDTO();
			dto.setReportId(report.getReportId());
			dto.setExamId(report.getExamId());
			dto.setUserId(report.getUserId());
			dto.setTotalMarks(report.getTotalMarks());
			dto.setPerformanceMetrics(report.getPerformanceMetrics());
			reportDTOs.add(dto);
		}
		return reportDTOs;
	}
}
