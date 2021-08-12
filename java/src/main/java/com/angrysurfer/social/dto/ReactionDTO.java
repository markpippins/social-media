package com.angrysurfer.social.dto;

import com.angrysurfer.social.model.Reaction;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReactionDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2161409918544474273L;

	private Long id;

	private String type;

	private String alias;

	public ReactionDTO() {

	}

	public static ReactionDTO fromReaction(Reaction reaction) {
		ReactionDTO result = new ReactionDTO();
		result.setId(reaction.getId());
		result.setType(reaction.getReactionType().toString());
		result.setAlias(reaction.getUser().getAlias());
		return result;
	}
}
