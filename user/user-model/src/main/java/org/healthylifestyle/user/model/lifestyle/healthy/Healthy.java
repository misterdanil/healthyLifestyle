package org.healthylifestyle.user.model.lifestyle.healthy;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Healthy {
	@Id
	@SequenceGenerator(name = "healthy_id_generator", sequenceName = "healthy_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "healthy_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "healthy_id", nullable = false)
	private List<Parameter> parameters;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

}
