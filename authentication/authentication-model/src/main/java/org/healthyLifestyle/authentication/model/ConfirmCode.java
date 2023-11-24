package org.healthyLifestyle.authentication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "confirm_code")
@Inheritance(strategy = InheritanceType.JOINED)
public class ConfirmCode {
	@Id
	@SequenceGenerator(name = "confirm_code_generator", sequenceName = "confirm_code_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "confirm_code_generator", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@Column(nullable = false)
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}