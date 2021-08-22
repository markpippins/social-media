package com.angrysurfer.social.repository;

import com.angrysurfer.social.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Optional<Profile> findByUserId(Long userId);

	void deleteByUserId(Long userId);
}
