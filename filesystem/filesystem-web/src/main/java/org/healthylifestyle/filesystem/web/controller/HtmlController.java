package org.healthylifestyle.filesystem.web.controller;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.filesystem.common.dto.SaveHtmlRequest;
import org.healthylifestyle.filesystem.model.Html;
import org.healthylifestyle.filesystem.service.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HtmlController {
	@Autowired
	private HtmlService htmlService;

	@PostMapping("/article/{arUuid}/fragment/{frUuid}/html")
	public ResponseEntity<Object> save(@RequestBody SaveHtmlRequest saveRequest, @PathVariable String arUuid,
			@PathVariable String frUuid) {

		Html html = htmlService.saveArticleFragmentHtml(saveRequest, arUuid, frUuid);

		return ResponseEntity.ok(html.getId());
	}
}
