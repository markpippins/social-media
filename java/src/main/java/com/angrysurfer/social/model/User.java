package com.angrysurfer.social.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.angrysurfer.social.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "User")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2747813660378401172L;

	@Id
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	@Getter
	private Long id;

	@Getter
	@Setter
	private String alias;

	@Getter
	@Setter
	private String email;

	@Lob
	@Getter
	@Setter
	private String avatarUrl = "https://picsum.photos/50/50";

	@OneToOne
	@Getter
	@Setter
	private Profile profile;

	@OneToMany(fetch = FetchType.EAGER)
	@Getter
	private Set<User> followers = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER)
	@Getter
	private Set<User> following = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER)
	@Getter
	private Set<User> friends = new HashSet<>();

	public User() {

	}

	public User(String alias, String email, String avatarUrl) {
		setAlias(alias);
		setEmail(email);
		setAvatarUrl(avatarUrl);
	}

	public static User fromDTO(UserDTO newUser) {
		return new User(newUser.getAlias(), newUser.getEmail(), newUser.getAvatarUrl());
	}

}