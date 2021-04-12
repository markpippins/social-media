package com.angrysurfer.social.controller;

import com.angrysurfer.social.ResourceNotFoundException;
import com.angrysurfer.social.dto.ForumDTO;
import com.angrysurfer.social.service.ForumService;

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
@RequestMapping(path = "/api/forums")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @PostMapping(path = "/add")
    public @ResponseBody ForumDTO addForum(@RequestParam String name) {
        return forumService.save(name);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<ForumDTO> getAllForums() {
        return forumService.findAll();
    }

    @GetMapping(path = "/id/{forumId}")
    public @ResponseBody ForumDTO getForum(@PathVariable Long forumId) {
        try {
            return forumService.findById(forumId);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/{name}")
    public @ResponseBody ForumDTO getForumByName(@PathVariable String name) {
        try {
            return forumService.findByName(name);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteForum(@PathVariable Long id) {
        forumService.delete(id);
    }

}