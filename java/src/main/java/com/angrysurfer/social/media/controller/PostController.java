package com.angrysurfer.social.media.controller;

import com.angrysurfer.social.ResourceNotFoundException;
import com.angrysurfer.social.media.dto.CommentDTO;
import com.angrysurfer.social.media.dto.PostDTO;
import com.angrysurfer.social.media.dto.PostStatDTO;
import com.angrysurfer.social.media.dto.ReactionDTO;
import com.angrysurfer.social.media.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping(path = "/add")
    public @ResponseBody PostDTO addPost(@RequestBody PostDTO post) {

        try {
            return postService.save(post);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(path = "/forums/{forumId}/add")
    public @ResponseBody PostDTO addPostToForum(@PathVariable Long forumId, @RequestBody PostDTO post) {

        try {
            return postService.addPostToForum(forumId, post);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Set<PostDTO> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping(path = "/{postId}/replies")
    public @ResponseBody Set<CommentDTO> getRepliesForPost(@PathVariable Long postId) {
        try {
            PostDTO post = postService.findById(postId);
            return post.getReplies();
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(path = "/{postId}/add/reaction")
    public @ResponseBody ReactionDTO addReaction(@PathVariable Long postId, @RequestBody ReactionDTO reaction) {
        try {
            return postService.addReaction(postId, reaction);
            // } catch (ResourceNotFoundException e) {
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(path = "/{postId}/remove/reaction")
    public void removeReaction(@PathVariable Long postId, @RequestBody ReactionDTO reaction) {
        try {
            postService.removeReaction(postId, reaction);
            // } catch (ResourceNotFoundException e) {
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(path = "/{postId}/rating/increment")
    public @ResponseBody PostStatDTO incrementRating(@PathVariable Long postId) {
        try {
            return postService.incrementRating(postId);
            // } catch (ResourceNotFoundException e) {
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(path = "/{postId}/rating/decrement")
    public @ResponseBody PostStatDTO decrementRating(@PathVariable Long postId) {
        try {
            return postService.decrementRating(postId);
            // } catch (ResourceNotFoundException e) {
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // @GetMapping(path = "/{id}/posts")
    // public @ResponseBody Set<Post> getUserPosts(@PathVariable Long id) {
    // Optional<User> user;
    // try {
    // user = userRepository.findById(id);
    // if (user.isPresent()) {
    // return user.get().getPosts();
    // }
    // } catch (IllegalArgumentException e) {
    // throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    // }

    // return Collections.emptySet();
    // }

}