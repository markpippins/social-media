package com.angrysurfer.social.media.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Profile")
//@Table(schema = "social")
public class Profile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6188258652004048094L;

	@Id
	@SequenceGenerator(name = "profile_sequence", sequenceName = "profile_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_sequence")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	@Getter
	private Long id;

	@Getter
	@Setter
	private String firstName;

	@Getter
	@Setter
	private String lastName;

	@Getter
	@Setter
	private String city;

	@Getter
	@Setter
	private String state;

	@Getter
	@Setter
	private String profileImageUrl;

	@OneToOne(fetch = FetchType.EAGER)
	@Getter
	@Setter
	private User user;

	@OneToMany(fetch = FetchType.LAZY)
	@Getter
	@Setter
	private Set<Interest> interests = new HashSet<>();

}
