package org.healthyLifestyle.authentication.common.dto;

import java.util.Date;

import org.healthyLifestyle.authentication.common.validation.annotation.Age;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignUpRequest {
	@Pattern(regexp = "\\p{Lu}\\p{L}+", message = "Имя некорректно")
	@Size(max = 128)
	private String firstName;
	@Pattern(regexp = "\\p{Lu}\\p{L}+", message = "Фамилия некорректна")
	@Size(max = 128)
	private String lastName;
	@NotBlank(message = "Укажите ваш псевдоним")
	private String username;
	@Email(message = "Введите корректную электронную почту")
	private String email;
	@Size(min = 8, message = "Пароль слишком короткий")
	private String password;
	@Age(message = "Вам должно быть 18 или больше")
	private Date birthDate;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
