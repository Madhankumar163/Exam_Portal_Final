package com.cts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.dto.ReportDTO;

@FeignClient(name = "AnalyticsService", path = "/report")
public interface AnalyticsClient {
	@PostMapping("/generate")
	ReportDTO generateReport(@RequestBody ReportDTO reportDTO);
}