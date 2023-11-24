package org.healthyLifestyle.authentication.model;

import java.util.Date;

import org.shop.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
	@Id
	@SequenceGenerator(name = "refresh_token_id_generator", sequenceName = "refresh_token_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "refresh_token_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, updatable = false)
	private String token;
	@Column(nullable = false, updatable = false)
	private Date issuedAt;
	@Column(nullable = false, updatable = false)
	private Date expiredAt;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	public RefreshToken() {
		super();
	}

	public RefreshToken(String token, Date issuedAt, Date expiredAt, User user) {
		super();
		this.token = token;
		this.issuedAt = issuedAt;
		this.expiredAt = expiredAt;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
