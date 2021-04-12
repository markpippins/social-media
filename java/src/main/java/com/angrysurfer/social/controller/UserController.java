package com.angrysurfer.social.controller;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.angrysurfer.social.ResourceNotFoundException;
import com.angrysurfer.social.dto.ProfileDTO;
import com.angrysurfer.social.dto.UserDTO;
import com.angrysurfer.social.model.Post;
import com.angrysurfer.social.model.User;
import com.angrysurfer.social.repository.UserRepository;
import com.angrysurfer.social.service.ProfileService;
import com.angrysurfer.social.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/add")
	public @ResponseBody UserDTO addUser(@RequestParam UserDTO newUser, @RequestParam ProfileDTO newProfile) {
		return userService.save(newUser);
	}

	@GetMapping(path = "/all")
	public @ResponseBody Set<UserDTO> getAllUsers() {
		return userService.findAll();
	}

	@PostMapping(path = "/delete/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.delete(id);
	}

	@GetMapping(path = "/id/{id}")
	public @ResponseBody UserDTO getUserById(@PathVariable Long id) {
		try {
			return userService.findById(id);
		} catch (ResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@GetMapping(path = "/alias/{alias}")
	public @ResponseBody UserDTO getUserByAlias(@PathVariable String alias) {
		try {
			return userService.findByAlias(alias);
		} catch (ResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

}