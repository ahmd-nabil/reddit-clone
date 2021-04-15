package com.redditclone.converters;

import com.redditclone.dto.CommentDto;
import com.redditclone.entities.Comment;
import org.springframework.core.convert.converter.Converter;

public class CommentToCommentDto implements Converter<Comment, CommentDto> {
    @Override
    public CommentDto convert(Comment comment) {
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .username(comment.getUser().getUsername())
                .postId(comment.getPost().getId())
                .createdTime(comment.getCreatedTime())
                .build();
        return commentDto;
    }
}
