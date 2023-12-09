package org.healthylifestyle.communication.common.dto;

import java.util.List;

import org.healthylifestyle.communication.model.settings.Invitation;
import org.healthylifestyle.communication.model.settings.Modification;
import org.healthylifestyle.communication.model.settings.Privacy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ChatCreatingRequest {
	@NotBlank(message = "{title.notBlank}")
	private String title;
	@NotEmpty(message = "{users.notEmpty}")
	private List<Long> userIds;
	@NotNull(message = "{setting.invitation}")
	private Invitation invitation;
	@NotNull(message = "{setting.modification}")
	private Modification modification;
	@NotNull(message = "{setting.privacy}")
	private Privacy privacy;
	private Long imageId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
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

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

}
