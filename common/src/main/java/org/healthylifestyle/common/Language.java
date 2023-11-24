package org.healthylifestyle.common;

import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "language")
public class Language {
	@Id
	@SequenceGenerator(name = "language_id_generator", sequenceName = "language_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "language_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private Locale name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locale getName() {
		return name;
	}

	public void setName(Locale name) {
		this.name = name;
	}

}
