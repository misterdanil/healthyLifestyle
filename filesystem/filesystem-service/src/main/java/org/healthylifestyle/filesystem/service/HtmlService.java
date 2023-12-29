package org.healthylifestyle.filesystem.service;

import org.healthylifestyle.filesystem.common.dto.SaveHtmlRequest;
import org.healthylifestyle.filesystem.model.Html;

public interface HtmlService {
	Html findById(Long id);
	
	Html saveArticleFragmentHtml(SaveHtmlRequest saveRequest, String articleUuid, String fragmentUuid);
}
