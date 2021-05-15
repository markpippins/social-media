package com.angrysurfer.social.repository;

import com.angrysurfer.social.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForumRepository extends JpaRepository<Forum, Long> {

	Optional<Forum> findByName(String name);

}
