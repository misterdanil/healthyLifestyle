package org.healthylifestyle.filesystem.model;

import java.io.InputStream;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table
public class File {
	@Id
	@SequenceGenerator(name = "file_id_generator", sequenceName = "file_sequence")
	@GeneratedValue(generator = "file_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name = "relative_path", nullable = false, unique = true)
	private String relativePath;
	@Transient
	private InputStream inputStream;
	@Column(nullable = false)
	private String type;
	@Column(nullable = false)
	private String mimeType;

	public Long getId() {
		return id;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
