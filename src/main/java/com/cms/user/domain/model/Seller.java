package com.cms.user.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cms.user.domain.SignUpForm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Seller extends BaseEntity{

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;
	private String name;
	private String password;
	private String phone;
	private LocalDate birth;

	private LocalDateTime verifyExpiredAt;
	private String verificationCode;
	private boolean verify;

	public static Seller from(SignUpForm form) {
		return Seller.builder()
			.email(form.getEmail().toLowerCase(Locale.ROOT))
			.password(form.getPassword())
			.name(form.getName())
			.birth(form.getBirth())
			.phone(form.getPhone())
			.verify(false)
			.build();
	}
}