package com.angrysurfer.social.controller;

import com.angrysurfer.social.dto.CommentDTO;
import com.angrysurfer.social.dto.ReactionDTO;
import com.angrysurfer.social.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/replies")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping(path = "/add")
	public @ResponseBody CommentDTO addComment(@RequestBody CommentDTO data) {

		try {
			return commentService.addComment(data);
			// } catch (ResourceNotFoundException e) {
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PostMapping(path = "/{commentId}/add/reaction")
	public ReactionDTO addReaction(@PathVariable Long commentId, @RequestBody ReactionDTO reaction) {
		try {
			return commentService.addReaction(commentId, reaction);
			// } catch (ResourceNotFoundException e) {
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PostMapping(path = "/{commentId}/remove/reaction")
	public void removeReaction(@PathVariable Long commentId, @RequestBody ReactionDTO reaction) {
		try {
			commentService.removeReaction(commentId, reaction);
			// } catch (ResourceNotFoundException e) {
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}