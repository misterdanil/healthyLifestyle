package org.healthylifestyle.filesystem.web.controller;

import org.healthylifestyle.filesystem.common.dto.SaveCssRequest;
import org.healthylifestyle.filesystem.model.Css;
import org.healthylifestyle.filesystem.service.CssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CssController {
	@Autowired
	private CssService cssService;

	@PostMapping("/article/{articleUuid}/fragment/{fragmentUuid}/css")
	public ResponseEntity<Object> save(@RequestBody SaveCssRequest saveRequest, @PathVariable String articleUuid,
			@PathVariable String fragmentUuid) {
		Css css = cssService.saveArticleFragmentCss(saveRequest, articleUuid, fragmentUuid);

		return ResponseEntity.ok(css.getId());
	}
}
