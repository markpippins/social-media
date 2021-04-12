package com.angrysurfer.social.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Reaction")
public class Reaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2157436062288147245L;

	public enum ReactionType {
		LIKE, LOVE, ANGER, SADNESS, SURPRISE
	}

	public Reaction() {

	}

	public Reaction(User user, ReactionType type) {
		setUser(user);
		setReactionType(type);
	}

	@Id
	@SequenceGenerator(name = "reaction_sequence", sequenceName = "reaction_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reaction_sequence")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	@Getter
	private Long id;

	@CreationTimestamp
	private LocalDateTime created;

	@Enumerated(EnumType.STRING)
	@Getter
	@Setter
	private ReactionType reactionType;

	@ManyToOne(fetch = FetchType.EAGER)
	@Getter
	@Setter
	private User user;
}
