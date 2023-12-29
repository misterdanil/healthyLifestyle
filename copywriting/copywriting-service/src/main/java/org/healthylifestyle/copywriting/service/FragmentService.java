package org.healthylifestyle.copywriting.service;

import java.util.List;

import org.healthylifestyle.copywriting.model.Fragment;

public interface FragmentService {
	List<Fragment> findAllByArticle(Long id);
}
