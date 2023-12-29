package org.healthylifestyle.copywriting.web.controller;

import java.util.List;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.copywriting.common.dto.MarkDto;
import org.healthylifestyle.copywriting.model.mark.Mark;
import org.healthylifestyle.copywriting.model.mark.Type;
import org.healthylifestyle.copywriting.service.MarkService;
import org.healthylifestyle.copywriting.service.mapper.MarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MarkController {
	@Autowired
	private MarkService markService;
	@Autowired
	private MarkMapper mapper;

	@PostMapping("/article/{id}/mark")
	public ResponseEntity<ErrorResult> mark(@PathVariable Long id, @RequestParam Type type) {
		try {
			markService.save(id, type);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/{id}/marks")
	public ResponseEntity<Object> findAllByUser(@PathVariable Long id) {
		List<Mark> marks = markService.findAllByUser(id);

		List<MarkDto> dtos = mapper.marksToDtos(marks);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/article/{id}/{type}")
	public ResponseEntity<Integer> countByArticleAndType(@PathVariable Long id, @PathVariable Type type) {
		int count = markService.countByArticleAndType(id, type);

		return ResponseEntity.ok(count);
	}

}
