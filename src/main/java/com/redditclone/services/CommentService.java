package com.redditclone.services;

import com.redditclone.converters.CommentDtoToComment;
import com.redditclone.dto.CommentDto;
import com.redditclone.entities.Comment;
import com.redditclone.entities.Post;
import com.redditclone.entities.User;
import com.redditclone.exceptions.RedditException;
import com.redditclone.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDtoToComment commentDtoToComment;
    private final UserService userService;
    private final PostService postService;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(()->new RedditException("Comment not found!"));
    }

    public Comment save(CommentDto commentDto) {
        Comment comment = commentDtoToComment.convert(commentDto);
        return commentRepository.save(comment);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findAllByUser(String username) {
        User user = userService.findByUsername(username);
        return commentRepository.findAllByUser(user);
    }

    public List<Comment> findAllByUser(Long userId) {
        User user = userService.findById(userId);
        return commentRepository.findAllByUser(user);
    }

    public List<Comment> findAllByUser(User user) {
        return commentRepository.findAllByUser(user);
    }

    public List<Comment> findAllByPost(Long postId) {
        Post post = postService.findById(postId);
        return commentRepository.findAllByPost(post);
    }

    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }
}
