package com.angrysurfer.social.media.repository;

import com.angrysurfer.social.media.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByAlias(String alias);
}
