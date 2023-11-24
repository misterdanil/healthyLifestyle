package org.healthylifestyle.copywriting.model;

import java.time.Instant;
import java.util.List;

public class Category {
	private Long id;
	private String title;
	private Category parentCategory;
	private List<Category> subCategory;
	private List<Article> articles;
	private Instant createdOn;
}
