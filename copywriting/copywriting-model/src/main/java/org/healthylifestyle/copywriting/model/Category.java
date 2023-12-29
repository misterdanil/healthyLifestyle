package org.healthylifestyle.copywriting.model;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.healthylifestyle.common.Translation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table
public class Category {
	@Id
	@SequenceGenerator(name = "category_id_generator", sequenceName = "category_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "category_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false, name = "original_title")
	private String originalTitle;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private List<Translation> translations;
	@ManyToOne
	@JoinColumn(name = "parentCategory_id")
	private Category parentCategory;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentCategory")
	private List<Category> subCategory;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<Article> articles;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

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

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Category> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<Category> subCategory) {
		this.subCategory = subCategory;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
