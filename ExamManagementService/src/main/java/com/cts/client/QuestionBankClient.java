package com.cts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.dto.QuestionDTO;

@FeignClient(name = "QuestionBankService", path = "/question")
public interface QuestionBankClient {
	@GetMapping("/{id}")
	QuestionDTO getQuestionById(@PathVariable Long id);
}
////