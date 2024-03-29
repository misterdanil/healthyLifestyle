package org.healthylifestyle.filesystem.service.impl;

import java.text.MessageFormat;

import org.healthylifestyle.filesystem.common.dto.SaveHtmlRequest;
import org.healthylifestyle.filesystem.model.File;
import org.healthylifestyle.filesystem.model.Html;
import org.healthylifestyle.filesystem.repository.HtmlRepository;
import org.healthylifestyle.filesystem.service.FileService;
import org.healthylifestyle.filesystem.service.HtmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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
	
	private static final Logger logger = LoggerFactory.getLogger(HtmlServiceImpl.class);

	@PostConstruct
	public void init() {
		articleFragmentHtmlPath = env.getProperty("article.fragment.html.path");
	}

	@Override
	public Html findById(Long id) {
		return htmlRepository.findById(id).orElse(null);
	}

	@Override
	public Html saveArticleFragmentHtml(SaveHtmlRequest saveRequest, String articleUuid, String fragmentUuid) {
		logger.debug("Start saving article fragment html");
		
		String path = MessageFormat.format(articleFragmentHtmlPath, articleUuid,
				fragmentUuid);
		
		logger.debug("Path is " + path);

		File file = fileService.save(saveRequest.getFilename(), saveRequest.getHtml(), path);
		
		logger.debug("File has been saved");
		
		Html html = new Html();
		html.setFile(file);

		html = htmlRepository.save(html);
		
		logger.debug("Html has been saved completely");

		return html;
	}

}
