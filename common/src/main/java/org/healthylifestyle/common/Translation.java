package org.healthylifestyle.common;

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
@Table(name = "translation")
public class Translation {
	@Id
	@SequenceGenerator(name = "translation_id_generator", sequenceName = "translation_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "translation_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, length = 500)
	private String value;
	@ManyToOne
	@JoinColumn(name = "language_id", nullable = false)
	private Language language;

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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
