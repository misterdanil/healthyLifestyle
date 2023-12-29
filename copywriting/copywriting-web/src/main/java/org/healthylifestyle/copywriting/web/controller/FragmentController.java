package org.healthylifestyle.copywriting.web.controller;

import java.util.List;

import org.healthylifestyle.copywriting.common.dto.FragmentDto;
import org.healthylifestyle.copywriting.model.Fragment;
import org.healthylifestyle.copywriting.service.FragmentService;
import org.healthylifestyle.copywriting.service.mapper.FragmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FragmentController {
	@Autowired
	private FragmentService fragmentService;
	@Autowired
	private FragmentMapper mapper;

	@GetMapping("/article/{id}/fragments")
	public ResponseEntity<List<FragmentDto>> findByArticleId(@PathVariable("id") Long articleId) {
		List<Fragment> fragments = fragmentService.findAllByArticle(articleId);

		List<FragmentDto> dtos = mapper.fragmentsToDtos(fragments);

		return ResponseEntity.ok(dtos);
	}
}
