package org.healthylifestyle.filesystem.service.impl;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.filesystem.model.File;
import org.healthylifestyle.filesystem.repository.FileRepository;
import org.healthylifestyle.filesystem.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
@PropertySource("classpath:absolute_path.properties")
public class FileServiceImpl implements FileService {
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private Environment env;
	private String ABSOLUTE_PATH;

	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@PostConstruct
	public void init() {
		ABSOLUTE_PATH = env.getProperty("filesystem.path");
	}

	@Override
	public File save(MultipartFile multipartFile, String relativePath) throws ValidationException {
		BindingResult validationResult = new MapBindingResult(new HashMap<>(), "file");

		String code = "file.size.zero";
		if (multipartFile.getSize() <= 0) {
			validationResult.reject(code, "Вы передали пустой файл");
		}

		if (validationResult.hasErrors()) {
			ErrorResult result = ErrorParser.getErrorResult(validationResult);

			throw new ValidationException("Exception occurred while saving file", result);
		}

		String fileName = multipartFile.getOriginalFilename();
		Path path = Paths.get(ABSOLUTE_PATH, relativePath, fileName);

		try {
			Files.createDirectories(path.getParent());
			Files.copy(multipartFile.getInputStream(), path);
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Exception occurred while saving file by path '%s'. Couldn't save file to filesystem",
							path.toString()),
					e);
		}

		File file = new File();
		file.setRelativePath(Paths.get(relativePath, fileName).toString());
		try {
			file.setMimeType(Files.probeContentType(path));
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Exception occurred while saving file. Couldn't extract mime type from path '%s'",
							path.toString()));
		}
		file.setType(FilenameUtils.getExtension(fileName));

		file = fileRepository.save(file);

		return file;
	}

	@Override
	public File save(String filename, String textFile, String relativePath) {
		Path path = Paths.get(ABSOLUTE_PATH, relativePath, filename);

		java.io.File savedFile = new java.io.File(path.getParent().toString());
		savedFile.mkdirs();
		savedFile = new java.io.File(path.toString());

		try (BufferedWriter w = new BufferedWriter(new FileWriter(savedFile))) {
			w.write(textFile);
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Exception occurred while saving file by path '%s'. Couldn't save file to filesystem",
							path.toString()),
					e);
		}

		File file = new File();
		file.setRelativePath(Paths.get(relativePath, filename).toString());
		try {
			file.setMimeType(Files.probeContentType(path));
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Exception occurred while saving file. Couldn't extract mime type from path '%s'",
							path.toString()));
		}
		file.setType(FilenameUtils.getExtension(relativePath));

		file = fileRepository.save(file);

		return file;
	}

	@Override
	public InputStream findResourceByPath(String relativePath) {
		Path path = Paths.get(ABSOLUTE_PATH, relativePath);

		try {
			return new FileInputStream(path.toString());
		} catch (FileNotFoundException e) {
			logger.error(
					"Exception occurred while finding resource by path '{}'. Couldn't get input stream by this path",
					path.toString(), e);

			return null;
		}
	}

	@Override
	public void remove(File file) {
		fileRepository.delete(file);

		Path path = Paths.get(ABSOLUTE_PATH, file.getRelativePath());

		try {
			Files.delete(path);
		} catch (IOException e) {
			throw new RuntimeException("Exception occurred while removing file", e);
		}
	}

}
