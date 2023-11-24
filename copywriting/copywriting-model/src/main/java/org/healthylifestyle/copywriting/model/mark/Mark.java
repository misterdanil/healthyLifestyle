package org.healthylifestyle.copywriting.model.mark;

import org.healthylifestyle.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Mark {
	@Id
	@SequenceGenerator(name = "mark_id_generator", sequenceName = "mark_sequence", allocationSize = 20)
	private Long id;
	@Enumerated
	private Type type;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
