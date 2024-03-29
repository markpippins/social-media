package com.angrysurfer.social.repository;

import com.angrysurfer.social.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByForumId(Long forumId, Pageable pageable);
}
