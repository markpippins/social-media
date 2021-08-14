package com.angrysurfer.social.media.dto;

import com.angrysurfer.social.media.model.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO extends AbstractContentDTO {

	private Long forumId;

	public PostDTO() {

	}

	public PostDTO(Post post) {
		super(post);
		this.setForumId(post.getForumId());
		this.setPostedTo((post.getPostedTo() == null ? null : post.getPostedTo().getAlias()));
	}

}
