package org.healthylifestyle.filesystem.service.impl;

import java.text.MessageFormat;

import org.healthylifestyle.filesystem.common.dto.SaveCssRequest;
import org.healthylifestyle.filesystem.model.Css;
import org.healthylifestyle.filesystem.model.File;
import org.healthylifestyle.filesystem.repository.CssRepository;
import org.healthylifestyle.filesystem.service.CssService;
import org.healthylifestyle.filesystem.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@PropertySource("classpath:absolute_path.properties")
public class CssServiceImpl implements CssService {
	@Autowired
	private CssRepository cssRepository;
	@Autowired
	private FileService fileService;
	@Autowired
	private Environment env;
	private String articleFragmentCssPath;

	private Logger logger = LoggerFactory.getLogger(CssServiceImpl.class);

	@PostConstruct
	public void init() {
		articleFragmentCssPath = env.getProperty("article.fragment.css.path");
	}

	@Override
	public Css findById(Long id) {
		return cssRepository.findById(id).orElse(null);
	}

	@Override
	public Css saveArticleFragmentCss(SaveCssRequest saveRequest, String articleUuid, String fragmentUuid) {
		logger.debug("Start saving article fragment css");

		String path = MessageFormat.format(articleFragmentCssPath, articleUuid, fragmentUuid);

		logger.debug("Path is " + path);

		File file = fileService.save(saveRequest.getFilename(), saveRequest.getCss(), path);

		logger.debug("File has been saved");

		Css css = new Css();
		css.setFile(file);

		css = cssRepository.save(css);

		logger.debug("Css has been saved completely");

		return css;
	}

}
