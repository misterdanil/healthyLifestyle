package org.healthylifestyle.user.model.lifestyle.healthy;

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
@Table
public class Parameter {
	@Id
	@SequenceGenerator(name = "parameter_id_generator", sequenceName = "parameter_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "parameter_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String value;
	@ManyToOne
	@JoinColumn(name = "parameter_type_id", nullable = false)
	private ParameterType parameterType;

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

	public ParameterType getParameterType() {
		return parameterType;
	}

	public void setParameterType(ParameterType parameterType) {
		this.parameterType = parameterType;
	}

}
