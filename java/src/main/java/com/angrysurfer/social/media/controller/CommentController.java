package com.angrysurfer.social.media.controller;

import com.angrysurfer.social.media.dto.CommentDTO;
import com.angrysurfer.social.media.dto.ReactionDTO;
import com.angrysurfer.social.media.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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