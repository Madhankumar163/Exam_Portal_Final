package com.cts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.dto.ReportDTO;
import com.cts.entity.Report;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ReportRepository;
import com.cts.service.ReportServiceImpl;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

	@Mock
	private ReportRepository reportRepository;

	@InjectMocks
	private ReportServiceImpl reportService;

	private Report report;
	private ReportDTO reportDTO;

	@BeforeEach
	void setUp() {
		report = Report.builder().reportId(1L).examId(1L).userId(1L).totalMarks(1).performanceMetrics("Marks: 1")
				.build();
		reportDTO = ReportDTO.builder().reportId(1L).examId(1L).userId(1L).totalMarks(1).performanceMetrics("Marks: 1")
				.build();
	}

	@Test
	void generateReport_success() {
		when(reportRepository.save(any(Report.class))).thenReturn(report);
		ReportDTO result = reportService.generateReport(reportDTO);
		assertEquals(reportDTO, result);
		verify(reportRepository).save(any(Report.class));
	}

	@Test
	void getReportsByUser_success() {
		when(reportRepository.findByUserId(1L)).thenReturn(Arrays.asList(report));
		var result = reportService.getReportsByUser(1L);
		assertEquals(1, result.size());
		assertEquals(reportDTO, result.get(0));
		verify(reportRepository).findByUserId(1L);
	}

	@Test
	void getReportsByUser_notFound() {
		when(reportRepository.findByUserId(1L)).thenReturn(Collections.emptyList());
		assertThrows(ResourceNotFoundException.class, () -> reportService.getReportsByUser(1L));
		verify(reportRepository).findByUserId(1L);
	}

	@Test
	void getReportsByExam_success() {
		when(reportRepository.findByExamId(1L)).thenReturn(Arrays.asList(report));
		var result = reportService.getReportsByExam(1L);
		assertEquals(1, result.size());
		assertEquals(reportDTO, result.get(0));
		verify(reportRepository).findByExamId(1L);
	}
}