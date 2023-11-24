package org.healthylifestyle.copywriting.model;

import java.util.List;

import org.healthylifestyle.copywriting.model.mark.Mark;

public class Article {
	private Long id;
	private String title;
	private List<Fragment> fragments;
	private List<Mark> marks;
}
