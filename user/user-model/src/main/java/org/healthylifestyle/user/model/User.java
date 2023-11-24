package org.healthylifestyle.user.model;

import java.util.List;

import org.healthylifestyle.filesystem.model.Image;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class User {
	@Id
	@SequenceGenerator(name = "user_id_generator", sequenceName = "user_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "user_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, length = 32)
	private String firstName;
	@Column(nullable = false, length = 32)
	private String lastName;
	@Column(nullable = false, length = 32)
	private String username;
	@Column(nullable = false, length = 255)
	private String email;
	@Column(nullable = false, length = 255)
	private String password;
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
	private List<Role> roles;
	@Column(nullable = false)
	private boolean isEnabled = false;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "avatar_id")
	private Image avatar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Image getAvatar() {
		return avatar;
	}

	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

}
