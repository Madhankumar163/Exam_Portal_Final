package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.ReportDTO;
import com.cts.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

	@Autowired
    private final ReportService reportService;

    @PostMapping("/generate")
    public ReportDTO generateReport(@RequestBody ReportDTO reportDTO) {
        return reportService.generateReport(reportDTO);
    }

    @GetMapping("/user/{userId}")
    public List<ReportDTO> getReportsByUser(@PathVariable Long userId) {
        return reportService.getReportsByUser(userId);
    }

    @GetMapping("/exam/{examId}")
    public List<ReportDTO> getReportsByExam(@PathVariable Long examId) {
        return reportService.getReportsByExam(examId);
    }
}
