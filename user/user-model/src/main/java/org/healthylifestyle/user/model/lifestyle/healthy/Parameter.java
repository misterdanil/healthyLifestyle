package org.healthylifestyle.user.model.lifestyle.healthy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Parameter {
	@Id
	@SequenceGenerator(name = "parameter_id_generator", sequenceName = "parameter_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "parameter_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String value;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTUAL;
	@ManyToOne
	@JoinColumn(name = "parameter_type_id", nullable = false)
	private ParameterType parameterType;
	@ManyToOne
	@JoinColumn(name = "healthy_id", nullable = false)
	private Healthy healthy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ParameterType getParameterType() {
		return parameterType;
	}

	public void setParameterType(ParameterType parameterType) {
		this.parameterType = parameterType;
	}

	public Healthy getHealthy() {
		return healthy;
	}

	public void setHealthy(Healthy healthy) {
		this.healthy = healthy;
	}

}
