package org.healthylifestyle.filesystem.web.controller;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.filesystem.common.dto.ImageDto;
import org.healthylifestyle.filesystem.model.File;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.service.ImageService;
import org.healthylifestyle.filesystem.service.dto.ImageSavingRequest;
import org.healthylifestyle.filesystem.service.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
	@Autowired
	private ImageService imageService;
	@Autowired
	private ImageMapper mapper;

	@PostMapping("/chat/image/{uuid}")
	public ResponseEntity<Object> save(@PathVariable String uuid, @RequestParam("image") MultipartFile image) {
		ImageSavingRequest saveRequest = new ImageSavingRequest();
		saveRequest.setFile(image);
		Image savedImage;
		try {
			savedImage = imageService.saveChatImage(saveRequest, uuid);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok(savedImage.getId());
	}

	@PostMapping("/article/{arUuid}/fragment/{frUuid}/image")
	public ResponseEntity<Object> save(@PathVariable String arUuid, @PathVariable String frUuid,
			@RequestParam("image") MultipartFile image) {
		ImageSavingRequest saveRequest = new ImageSavingRequest();
		saveRequest.setFile(image);

		Image savedImage;
		try {
			savedImage = imageService.saveArticleFragmentImage(saveRequest, arUuid, frUuid);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok(savedImage.getId());
	}

	@PostMapping("/article/{arUuid}/image")
	public ResponseEntity<Object> saveArticleImage(@PathVariable String arUuid,
			@RequestParam("image") MultipartFile image) {
		ImageSavingRequest saveRequest = new ImageSavingRequest();
		saveRequest.setFile(image);

		Image savedImage;
		try {
			savedImage = imageService.saveArticleImage(saveRequest, arUuid);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok(savedImage.getId());
	}

	@PostMapping("/chat/{chatUuid}/message/{messageUuid}/image")
	public ResponseEntity<Object> saveMessageImage(@PathVariable String chatUuid, @PathVariable String messageUuid,
			@RequestParam("image") MultipartFile image) {
		ImageSavingRequest saveRequest = new ImageSavingRequest();
		saveRequest.setFile(image);

		Image savedImage;
		try {
			savedImage = imageService.saveMessageImage(saveRequest, chatUuid, messageUuid);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok(savedImage.getId());
	}

	@GetMapping("/article/*/fragment/{fragmentId}/images")
	public ResponseEntity<Object> findByFragment(@PathVariable Long fragmentId) {
		List<Image> images = imageService.findByFragment(fragmentId);

		List<ImageDto> dtos = mapper.imagesToDtos(images);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/chats/*/messages/{messageId}/images")
	public ResponseEntity<Object> findByMessage(@PathVariable Long messageId) {
		List<Image> images = imageService.findByMessage(messageId);

		List<ImageDto> dtos = mapper.imagesToDtos(images);

		return ResponseEntity.ok(dtos);
	}
}
