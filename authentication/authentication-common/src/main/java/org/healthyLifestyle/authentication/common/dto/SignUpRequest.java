package org.healthyLifestyle.authentication.common.dto;

import java.time.Instant;

import org.shop.authentication.common.validation.annotation.Age;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignUpRequest {
	@Pattern(regexp = "\\p{Lu}\\p{L}+")
	@Size(max = 128)
	private String firstName;
	@Pattern(regexp = "\\p{Lu}\\p{L}+")
	@Size(max = 128)
	private String lastName;
	@Email
	private String email;
	@Size(min = 8)
	private String password;
	@Age
	private Instant birthDate;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public Instant getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Instant birthDate) {
		this.birthDate = birthDate;
	}

}