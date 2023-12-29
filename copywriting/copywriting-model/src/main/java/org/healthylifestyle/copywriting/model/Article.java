package org.healthylifestyle.copywriting.model;

import java.util.Date;
import java.util.List;

import org.healthylifestyle.common.Translation;
import org.healthylifestyle.copywriting.model.mark.Mark;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table
public class Article {
	@Id
	@SequenceGenerator(name = "article_id_generator", sequenceName = "article_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "article_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String uuid;
	@Column(nullable = false, name = "original_title")
	private String originalTitle;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "article_id")
	private List<Translation> translations;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "article_id", nullable = false)
	private List<Fragment> fragments;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Mark> marks;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn = new Date();

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

	public List<Fragment> getFragments() {
		return fragments;
	}

	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}

	public List<Mark> getMarks() {
		return marks;
	}

	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
