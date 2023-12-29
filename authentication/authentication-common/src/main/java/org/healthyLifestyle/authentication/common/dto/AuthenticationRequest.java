package org.healthyLifestyle.authentication.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class AuthenticationRequest {
	@Email(message = "Введите корректную почту")
	private String email;
	@Pattern(regexp = "^[A-Za-z0-9]{8, }$", message = "Пароль некорректный")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
