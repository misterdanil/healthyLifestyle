package org.healthylifestyle.filesystem.common.dto;

public class SaveCssRequest {
	private String articleUuid;
	private String fragmentUuid;
	private String filename;
	private String css;

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

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

}
