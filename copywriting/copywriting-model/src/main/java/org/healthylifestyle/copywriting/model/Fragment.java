package org.healthylifestyle.copywriting.model;

import java.util.List;

import org.healthylifestyle.filesystem.model.Css;
import org.healthylifestyle.filesystem.model.Html;
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
import jakarta.persistence.OneToOne;
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
	private String uuid;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "html_id", nullable = false)
	private Html html;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "css_id", nullable = false)
	private Css css;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fragment_id")
	private List<Image> images;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fragment_id")
	private List<Video> videos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Html getHtml() {
		return html;
	}

	public void setHtml(Html html) {
		this.html = html;
	}

	public Css getCss() {
		return css;
	}

	public void setCss(Css css) {
		this.css = css;
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
