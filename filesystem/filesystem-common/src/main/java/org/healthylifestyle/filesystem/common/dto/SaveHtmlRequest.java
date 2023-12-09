package org.healthylifestyle.filesystem.common.dto;

public class SaveHtmlRequest {
	private String articleUuid;
	private String fragmentUuid;
	private String filename;
	private String html;

	public String getArticleUuid() {
		return articleUuid;
	}

	public void setArticleUuid(String articleUuid) {
		this.articleUuid = articleUuid;
	}

	public String getFragmentUuid() {
		return fragmentUuid;
	}

	public void setFragmentUuid(String fragmentUuid) {
		this.fragmentUuid = fragmentUuid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
