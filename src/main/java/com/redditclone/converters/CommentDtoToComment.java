package com.redditclone.converters;

import com.redditclone.dto.CommentDto;
import com.redditclone.entities.Comment;
import com.redditclone.entities.Post;
import com.redditclone.entities.User;
import com.redditclone.services.AuthService;
import com.redditclone.services.PostService;
import com.redditclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class CommentDtoToComment implements Converter<CommentDto, Comment> {

    private final AuthService authService;
    private final UserService userService;
    private final PostService postService;

    @Override
    public Comment convert(CommentDto commentDto) {
        User user = userService.findByUsername(authService.getCurrentUserAuthentication().getName());
        Post post = postService.findById(commentDto.getPostId());
        Comment comment = Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .user(user)
                .post(post)
                .createdTime(Instant.now())
                .build();
        return comment;
    }
}
