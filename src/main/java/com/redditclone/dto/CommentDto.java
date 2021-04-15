package com.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private String username;
    private Long postId;
    private Instant createdTime;
}
