package com.angrysurfer.social.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angrysurfer.social.model.Forum;

public interface ForumRepository extends JpaRepository<Forum, Long> {

	Optional<Forum> findByName(String name);

}
