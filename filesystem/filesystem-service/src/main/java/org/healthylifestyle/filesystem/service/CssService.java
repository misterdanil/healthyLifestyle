package org.healthylifestyle.filesystem.service;

import org.healthylifestyle.filesystem.common.dto.SaveCssRequest;
import org.healthylifestyle.filesystem.model.Css;

public interface CssService {
	Css findById(Long id);
	
	Css saveArticleFragmentCss(SaveCssRequest saveRequest);
}
