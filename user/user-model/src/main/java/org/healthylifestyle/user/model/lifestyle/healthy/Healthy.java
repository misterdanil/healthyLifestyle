package org.healthylifestyle.user.model.lifestyle.healthy;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Healthy {
	@Id
	@SequenceGenerator(name = "healthy_id_generator", sequenceName = "healthy_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "healthy_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "healthy")
	private List<Parameter> parameters;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new ArrayList<>();
		}
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
