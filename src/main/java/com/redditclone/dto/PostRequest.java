package com.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequest {
    private Long id;
    private String title;
    private String description;
    private Long subredditId;
}
