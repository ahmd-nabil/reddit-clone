package com.redditclone.controllers;

import com.redditclone.converters.PostToPostResponse;
import com.redditclone.dto.PostRequest;
import com.redditclone.dto.PostResponse;
import com.redditclone.entities.Post;
import com.redditclone.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostToPostResponse postToPostResponse;

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.findAll().stream().map(post -> postToPostResponse.convert(post)).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        Post post =  postService.findById(id);
        PostResponse postResponse = postToPostResponse.convert(post);
        return postResponse;
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
        return postService.findAllByUsername(username).stream().map(postToPostResponse::convert).collect(Collectors.toList());
    }

    @GetMapping("/subreddit/{subredditId}")
    public List<PostResponse> getAllBySubreddit(@PathVariable Long subredditId) {
        return postService.findAllBySubredditId(subredditId).stream().map(postToPostResponse::convert).collect(Collectors.toList());
    }
}
