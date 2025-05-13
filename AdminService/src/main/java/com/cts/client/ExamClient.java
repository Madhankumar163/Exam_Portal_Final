package com.cts.client;

import com.cts.dto.ExamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ExamManagementService", path = "/exam")
public interface ExamClient {
    @PostMapping
    ExamDTO createExam(@RequestBody ExamDTO examDTO);

    @PutMapping("/{id}")
    ExamDTO updateExam(@PathVariable Long id, @RequestBody ExamDTO examDTO);

    @DeleteMapping("/{id}")
    void deleteExam(@PathVariable Long id);

    @GetMapping("/{id}")
    ExamDTO getExamById(@PathVariable Long id);
}