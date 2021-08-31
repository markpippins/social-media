package com.angrysurfer.social.service;

import com.angrysurfer.ResourceNotFoundException;
import com.angrysurfer.social.dto.ProfileDTO;
import com.angrysurfer.social.model.Profile;
import com.angrysurfer.social.model.User;
import com.angrysurfer.social.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	public ProfileDTO findByUserId(Long userId) throws ResourceNotFoundException {
		Optional< Profile > profile = profileRepository.findByUserId(userId);
		if (profile.isPresent())
			return ProfileDTO.fromProfile(profile.get());

		throw new ResourceNotFoundException("Profile not found.");
	}

	public String deleteByUserId(Long userId) {
		profileRepository.deleteByUserId(userId);
		return "redirect:/user/all";
	}

	public Profile save(User user, String firstName, String lastName) {
		Profile p = new Profile();

		p.setUser(user);
		p.setFirstName(firstName);
		p.setLastName(lastName);

		return profileRepository.save(p);
	}

}