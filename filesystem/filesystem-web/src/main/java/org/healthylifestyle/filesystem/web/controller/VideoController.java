package org.healthylifestyle.filesystem.web.controller;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.filesystem.service.VideoService;
import org.healthylifestyle.filesystem.service.dto.VideoSavingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class VideoController {
	@Autowired
	private VideoService videoService;

	@PostMapping("/article/{arUuid}/fragment/{frUuid}/video")
	public ResponseEntity<ErrorResult> save(@PathVariable String arUuid, @PathVariable String frUuid,
			@RequestParam("image") MultipartFile image) {
		VideoSavingRequest saveRequest = new VideoSavingRequest();
		saveRequest.setFile(image);

		try {
			videoService.saveArticleFragmentVideo(saveRequest, arUuid, frUuid);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

}
