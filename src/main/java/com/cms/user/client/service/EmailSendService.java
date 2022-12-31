package com.cms.user.client.service;

import org.springframework.stereotype.Service;

import com.cms.user.client.MailgunClient;
import com.cms.user.client.mailgun.SendMailForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSendService {
	private final MailgunClient mailgunClient;

	public String sendEmail(){

		SendMailForm form = SendMailForm.builder()
			.from("zerobase@test.com")
			.to("ksj2083@naver.com")
			.subject("Test email from zerobase")
			.text("my text")
			.build();

		return mailgunClient.sendEmail(form).getBody();
	}
}
