package com.cms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.user.client.service.EmailSendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
	private final EmailSendService emailSendService;

	@GetMapping
	public String sendTestEmail(){
		return emailSendService.sendEmail();
	}
}
