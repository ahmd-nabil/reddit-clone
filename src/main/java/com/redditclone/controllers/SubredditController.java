package com.redditclone.controllers;

import com.redditclone.entities.Subreddit;
import com.redditclone.services.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;

    @GetMapping
    public List<Subreddit> getAllSubreddits() {
        return subredditService.findAll();
    }

    @GetMapping("{id}")
    public Subreddit getSubredditById(@PathVariable Long id) {
        return subredditService.findById(id);
    }

    @PostMapping
    public ResponseEntity saveSubreddit(@RequestBody Subreddit subreddit) {
        subredditService.save(subreddit);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteSubreddit(@PathVariable Long id) {
        subredditService.deleteById(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("{name}")
    public Subreddit getSubredditByName(@PathVariable String name) {
        return subredditService.findByName(name);
    }
}
