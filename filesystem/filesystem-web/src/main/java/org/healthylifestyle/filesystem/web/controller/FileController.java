package org.healthylifestyle.filesystem.web.controller;

import java.io.InputStream;

import org.healthylifestyle.filesystem.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;

	@GetMapping("/image")
	@ResponseBody
	public InputStreamResource findByArticle(@RequestParam String path) {
		InputStream inputStream = fileService.findResourceByPath(path);

		return new InputStreamResource(inputStream);
	}
}
