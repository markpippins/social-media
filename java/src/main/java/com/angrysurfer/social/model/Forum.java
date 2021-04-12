package com.angrysurfer.social.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity()
public class Forum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2527484659765374240L;

	@Id
	@SequenceGenerator(name = "forum_sequence", sequenceName = "forum_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forum_sequence")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	@Getter
	private Long id;

	@Getter
	@Setter
	private String name;

	@ManyToMany
	@JoinTable(name = "forum_members")
	@Getter
	@Setter
	private Set<User> members = new HashSet<>();

	public Forum() {
	}

	public Forum(String name) {
		this.setName(name);
	}

	public void addMember(User user) {
		this.getMembers().add(user);
	}
}
