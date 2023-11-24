package org.healthylifestyle.communication.model.settings;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Setting {
	@Id
	@SequenceGenerator(name = "setting_id_generator", sequenceName = "setting_sequence")
	@GeneratedValue(generator = "setting_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Enumerated
	private Privacy privacy;
	@Enumerated
	private Modification modification;
	@Enumerated
	private Invitation invitation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	public Modification getModification() {
		return modification;
	}

	public void setModification(Modification modification) {
		this.modification = modification;
	}

	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

}
