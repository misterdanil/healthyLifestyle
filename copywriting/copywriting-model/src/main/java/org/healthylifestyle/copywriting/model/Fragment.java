package org.healthylifestyle.copywriting.model;

import java.util.List;

import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.model.Video;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class Fragment {
	@Id
	@SequenceGenerator(name = "fragment_id_generator", sequenceName = "fragment_sequence")
	@GeneratedValue(generator = "fragment_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String text;
	private String styles;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fragment_id", nullable = false)
	private List<Image> images;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fragment_id", nullable = false)
	private List<Video> videos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStyles() {
		return styles;
	}

	public void setStyles(String styles) {
		this.styles = styles;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

}
