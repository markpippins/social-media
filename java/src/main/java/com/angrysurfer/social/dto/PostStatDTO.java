package com.angrysurfer.social.dto;

import com.angrysurfer.social.model.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostStatDTO {

    private Long id;

    private Long rating;

    public PostStatDTO() {

    }

    public PostStatDTO(Post post) {
        this.setId(post.getId());
        this.setRating(post.getRating());
    }

}
