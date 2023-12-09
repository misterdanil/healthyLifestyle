package org.healthylifestyle.filesystem.service.impl;

import java.text.MessageFormat;

import org.apache.commons.io.FilenameUtils;
import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.filesystem.common.dto.SaveHtmlRequest;
import org.healthylifestyle.filesystem.model.File;
import org.healthylifestyle.filesystem.model.Html;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.repository.HtmlRepository;
import org.healthylifestyle.filesystem.service.FileService;
import org.healthylifestyle.filesystem.service.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
@PropertySource("classpath:absolute_path.properties")
public class HtmlServiceImpl implements HtmlService {
	@Autowired
	private HtmlRepository htmlRepository;
	@Autowired
	private FileService fileService;
	@Autowired
	private Environment env;
	private String articleFragmentHtmlPath;

	@PostConstruct
	public void init() {
		articleFragmentHtmlPath = env.getProperty("article.fragment.html.path");
	}

	@Override
	public Html findById(Long id) {
		return htmlRepository.findById(id).orElse(null);
	}

	@Override
	public Html saveArticleFragmentHtml(SaveHtmlRequest saveRequest) {
		String path = MessageFormat.format(articleFragmentHtmlPath, saveRequest.getArticleUuid(),
				saveRequest.getFragmentUuid());

		File file = fileService.save(saveRequest.getFilename(), saveRequest.getHtml(), path);

		Html html = new Html();
		html.setFile(file);

		html = htmlRepository.save(html);

		return html;
	}

}
