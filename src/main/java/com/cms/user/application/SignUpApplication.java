package com.cms.user.application;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.cms.user.client.MailgunClient;
import com.cms.user.client.mailgun.SendMailForm;
import com.cms.user.domain.SignUpForm;
import com.cms.user.domain.model.Customer;
import com.cms.user.domain.model.Seller;
import com.cms.user.exception.CustomException;
import com.cms.user.exception.ErrorCode;
import com.cms.user.service.customer.SignUpCustomerService;
import com.cms.user.service.seller.SellerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {
	private final MailgunClient mailgunClient;
	private final SignUpCustomerService signUpCustomerService;
	private final SellerService sellerService;

	public void customerVerify(String email, String code){
		signUpCustomerService.verifyEmail(email, code);
	}

	public String customerSignUp(SignUpForm form){
		if(signUpCustomerService.isEmailExist(form.getEmail())) {
			throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
			//exception
		}else {
			Customer c= signUpCustomerService.signUp(form);
			LocalDateTime now = LocalDateTime.now();

			String code =getRandomCode();
			SendMailForm sendMailForm = SendMailForm.builder()
										.from("test@test.com")
										.to(form.getEmail())
										.subject("Verification Email!")
										.text(getVerificationEmailBody(c.getEmail(), c.getName(),"customer", code))
										.build();
			mailgunClient.sendEmail(sendMailForm);
			signUpCustomerService.ChangeCustomerValidateEmail(c.getId(), code);
			return "회원 가입에 성공하였습니다.";
		}
	}

	public void sellerVerify(String email, String code){
		sellerService.verifyEmail(email, code);
	}

	public String sellerSignUp(SignUpForm form){
		if(sellerService.isEmailExist(form.getEmail())){
			// exception
			throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
		}else{
			Seller s = sellerService.signUp(form);

			String code = getRandomCode();

			SendMailForm sendMailForm = SendMailForm.builder()
							.from("test@test.com")
							.to(form.getEmail())
							.subject("Verification Email!")
							.text(getVerificationEmailBody(form.getEmail(), form.getName(), "seller", code))
							.build();

			String result = mailgunClient.sendEmail(sendMailForm).getBody();

			log.info("Send email result : " + result);

			sellerService.changeSellerValidateEmail(s.getId(), code);

			return "회원 가입에 성공하였습니다.";
		}
	}

	private String getRandomCode(){
		return RandomStringUtils.random(10, true, true);
	}

	private String getVerificationEmailBody(String email, String name,String type, String code){
		StringBuilder builder = new StringBuilder();

		return builder.append("Hello ").append(name).append("! Please Click Link for verification. \n\n")
			.append("http://localhost:8082/signup/"+type+"/verify/customer?email=")
			.append(email)
			.append("&code=")
			.append(code)
			.toString();
	}
}
