package com.angrysurfer.social.controller;

import com.angrysurfer.ResourceNotFoundException;
import com.angrysurfer.social.dto.ProfileDTO;
import com.angrysurfer.social.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/profiles")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@GetMapping(path = "/userId/{userId}")
	public @ResponseBody
	ProfileDTO getProfileByUserId(@PathVariable Long userId) {
		try {
			return profileService.findByUserId(userId);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (ResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

}