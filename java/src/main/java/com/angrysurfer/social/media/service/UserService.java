package com.angrysurfer.social.media.service;

import com.angrysurfer.social.ResourceNotFoundException;
import com.angrysurfer.social.media.dto.UserDTO;
import com.angrysurfer.social.media.model.Profile;
import com.angrysurfer.social.media.model.User;
import com.angrysurfer.social.media.repository.ProfileRepository;
import com.angrysurfer.social.media.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileRepository profileRepository;

	public void delete(Long userId) {
		userRepository.deleteById(userId);
	}

	public Set<UserDTO> findAll() {
		HashSet<User> result = new HashSet<>();
		userRepository.findAll().forEach(result::add);
		return result.stream().map(user -> UserDTO.fromUser(user)).collect(Collectors.toSet());
	}

	public UserDTO findById(Long userId) throws ResourceNotFoundException {
		Optional<User> result = userRepository.findById(userId);
		if (result.isPresent())
			return UserDTO.fromUser(result.get());

		throw new ResourceNotFoundException("User ".concat(Long.toString(userId).concat(" not found.")));
	}

	public UserDTO findByAlias(String alias) throws ResourceNotFoundException {

		UserDTO result;

		Optional<User> user = userRepository.findByAlias(alias);
		if (user.isPresent()) {
			Optional<Profile> profile = profileRepository.findByUserId(user.get().getId());
			if (profile.isPresent()) {
				result = UserDTO.fromUser(user.get());
				result.setProfileImageUrl(profile.get().getProfileImageUrl());
			} else {
				result = UserDTO.fromUser(user.get());
			}

			return result;
		}

		throw new ResourceNotFoundException("User ".concat(alias).concat(" not found."));
	}

	public UserDTO save(UserDTO newUser) {
		return UserDTO.fromUser(userRepository.save(User.fromDTO(newUser)));
	}

	public UserDTO update(User user) {
		return UserDTO.fromUser(userRepository.save(user));
	}

}