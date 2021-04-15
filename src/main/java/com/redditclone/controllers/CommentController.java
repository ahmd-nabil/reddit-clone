package com.redditclone.controllers;


import com.redditclone.converters.CommentToCommentDto;
import com.redditclone.dto.CommentDto;
import com.redditclone.services.CommentService;
import com.redditclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentToCommentDto commentToCommentDto;
    private final UserService userService;

    @GetMapping
    public List<CommentDto> getAllComments() {
        return commentService.findAll().stream().map(comment -> commentToCommentDto.convert(comment)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CommentDto getCommentById(@PathVariable Long id) {
        return commentToCommentDto.convert(commentService.findById(id));
    }

    @PostMapping
    public ResponseEntity saveComment(@RequestBody CommentDto commentDto) {
        commentService.save(commentDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/user/{username}")
    public List<CommentDto> getAllCommentsByUser(@PathVariable String username) {
        return commentService.findAllByUser(username).stream().map(comment -> commentToCommentDto.convert(comment)).collect(Collectors.toList());
    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> getAllCommentsByPost(@PathVariable Long postId) {
        return commentService.findAllByPost(postId).stream().map(comment -> commentToCommentDto.convert(comment)).collect(Collectors.toList());
    }
}
