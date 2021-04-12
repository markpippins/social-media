package com.angrysurfer.social.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angrysurfer.social.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Optional<Profile> findByUserId(Long userId);

	void deleteByUserId(Long userId);
}
