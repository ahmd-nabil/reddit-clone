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
public class PostResponse {
    private Long id;
    private String username;
    private String subredditName;
    private String title;
    private String description;
    private String url;
    private Integer votesCount;
    private Integer commentsCount;
    private Instant createdTime;
    private String timeAgo;
}
