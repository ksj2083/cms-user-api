package com.cms.user.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
	ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다"),
	WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
	EXPIRE_CODE(HttpStatus.BAD_REQUEST, "인증 시간이 만료되었습니다."),


	/// login
	LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해주세요."),
	ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다.");

	private final HttpStatus httpStatus;
	private final String detail;
}
