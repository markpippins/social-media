package com.angrysurfer.social.service;

import com.angrysurfer.social.model.Reaction;
import com.angrysurfer.social.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ReactionService {

	@Autowired
	private ReactionRepository reactionRepository;

	public String delete(Long reactionId) {
		reactionRepository.deleteById(reactionId);
		return "redirect:/Reaction/all";
	}

	public Optional<Reaction> findById(Long reactionId) {
		return reactionRepository.findById(reactionId);
	}

	public Set<Reaction> findAll() {
		HashSet<Reaction> result = new HashSet<>();
		reactionRepository.findAll().forEach(result::add);
		return result;
	}

	public Reaction save(Reaction n) {
		return reactionRepository.save(n);
	}

	public void update(Long id) {
		reactionRepository.deleteById(id);
	}

}