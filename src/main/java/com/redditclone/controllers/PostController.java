package com.redditclone.controllers;

import com.redditclone.dto.PostRequest;
import com.redditclone.dto.PostResponse;
import com.redditclone.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping
    public ResponseEntity savePost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/user/{username}")
    public List<PostResponse> getAllByUser(@PathVariable String username) {
        return postService.findAllByUsername(username);
    }

    @GetMapping("/subreddit/{subredditId}")
    public List<PostResponse> getAllBySubreddit(@PathVariable Long subredditId) {
        return postService.findAllBySubredditId(subredditId);
    }
}
