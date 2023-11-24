package org.healthylifestyle.user.model.lifestyle.healthy.measure;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "measure")
public class Measure {
	@Id
	@SequenceGenerator(name = "measure_id_generator", sequenceName = "measure_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "measure_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Enumerated
	private Type type;
	@Enumerated
	private Unit unit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}

