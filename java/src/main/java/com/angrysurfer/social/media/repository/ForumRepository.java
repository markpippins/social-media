package com.angrysurfer.social.media.repository;

import com.angrysurfer.social.media.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForumRepository extends JpaRepository<Forum, Long> {

	Optional<Forum> findByName(String name);

}
