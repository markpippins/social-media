package com.angrysurfer.social.media.dto;

import com.angrysurfer.social.media.model.Forum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ForumDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5881692384954888978L;

	@Getter
	private Long id;

	@Getter
	@Setter
	private String name;

	@Getter
	private Set<UserDTO> members = new HashSet<>();

	public ForumDTO() {
	}

	public static ForumDTO fromForum(Forum forum) {

		ForumDTO result = new ForumDTO();
		result.id = forum.getId();
		result.setName(forum.getName());
		forum.getMembers().forEach(member -> result.getMembers().add(UserDTO.fromUser(member)));

		return result;
	}

}
