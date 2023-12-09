package org.healthylifestyle.user.model.lifestyle.healthy;

import java.util.List;

import org.healthylifestyle.common.Translation;
import org.healthylifestyle.user.model.lifestyle.healthy.measure.Measure;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "parameter_type")
public class ParameterType {
	@Id
	@SequenceGenerator(name = "parameter_type_id_generator", sequenceName = "parameter_type_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "parameter_type_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String originalTitle;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "parameter_type_id")
	private List<Translation> translations;
	@Column(nullable = false)
	@Enumerated
	private ClassType type;
	@ManyToOne
	@JoinColumn(name = "measure_id")
	private Measure measure;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public List<Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}

	public ClassType getType() {
		return type;
	}

	public void setType(ClassType type) {
		this.type = type;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

}
