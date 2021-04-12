package com.angrysurfer.social.dto;

import com.angrysurfer.social.model.Comment;
import com.angrysurfer.social.model.IContent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends AbstractContentDTO {

	private Long postId;

	private Long parentId;

	public CommentDTO() {

	}

	public CommentDTO(Comment comment) {
		super(comment);
	}

	public CommentDTO(IContent post, IContent comment) {
		super(comment);
		setPostId(post.getId());
		setPostedToAlias(post.getPostedBy().getAlias());
	}

	public CommentDTO(IContent comment, Long parentId, String postedToAlias) {
		super(comment);
		setParentId(parentId);
		setPostedToAlias(postedToAlias);
	}

}
