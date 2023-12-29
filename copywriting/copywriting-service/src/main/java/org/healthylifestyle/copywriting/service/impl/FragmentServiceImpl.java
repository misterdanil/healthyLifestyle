package org.healthylifestyle.copywriting.service.impl;

import java.util.List;

import org.healthylifestyle.copywriting.model.Fragment;
import org.healthylifestyle.copywriting.repository.FragmentRepository;
import org.healthylifestyle.copywriting.service.FragmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FragmentServiceImpl implements FragmentService {
	@Autowired
	private FragmentRepository fragmentRepository;

	@Override
	public List<Fragment> findAllByArticle(Long id) {
		return fragmentRepository.findAllByArticle(id);
	}

}
