package com.angrysurfer.social.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

import com.angrysurfer.social.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Set<Comment> findByPostId(Long postId);

	Page<Comment> findByPostId(Long postId, Pageable pageable);
}
