package org.healthylifestyle.filesystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Image {
	@Id
	@SequenceGenerator(name = "image_id_generator", sequenceName = "image_sequence")
	@GeneratedValue(generator = "image_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
