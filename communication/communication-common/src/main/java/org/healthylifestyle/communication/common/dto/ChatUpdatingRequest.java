package org.healthylifestyle.communication.common.dto;

import java.util.List;

import org.healthylifestyle.communication.model.settings.Invitation;
import org.healthylifestyle.communication.model.settings.Modification;
import org.healthylifestyle.communication.model.settings.Privacy;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class ChatUpdatingRequest {
	@NotNull(message = "{chat.update.id.notnull}")
	private Long id;
	private String title;
	private Invitation invitation;
	private Modification modification;
	private Privacy privacy;
	private List<Long> adminCandidateIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

	public Modification getModification() {
		return modification;
	}

	public void setModification(Modification modification) {
		this.modification = modification;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	public List<Long> getAdminCandidateIds() {
		return adminCandidateIds;
	}

	public void setAdminCandidateIds(List<Long> adminCandidateIds) {
		this.adminCandidateIds = adminCandidateIds;
	}

}
