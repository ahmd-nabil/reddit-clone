package com.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String url;
    private String description;
    private Integer votesCount;
    private Long userId;
    private Long subredditId;
    private Integer commentsCount;
}
