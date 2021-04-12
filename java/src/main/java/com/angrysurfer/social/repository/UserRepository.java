package com.angrysurfer.social.repository;

import java.util.Optional;

import com.angrysurfer.social.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByAlias(String alias);
}
