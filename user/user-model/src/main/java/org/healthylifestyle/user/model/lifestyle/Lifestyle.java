package org.healthylifestyle.user.model.lifestyle;

import java.util.List;

import org.healthylifestyle.user.model.lifestyle.healthy.Healthy;
import org.healthylifestyle.user.model.lifestyle.healthy.event.Event;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Lifestyle {
	@Id
	@SequenceGenerator(name = "lifestyle_id_generator", sequenceName = "lifestyle_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "lifestyle_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	private Healthy healthy;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Event> events;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Healthy getHealthy() {
		return healthy;
	}

	public void setHealthy(Healthy healthy) {
		this.healthy = healthy;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
