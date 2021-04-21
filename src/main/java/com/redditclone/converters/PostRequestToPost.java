package com.redditclone.converters;

import com.redditclone.dto.PostRequest;
import com.redditclone.entities.Post;
import com.redditclone.entities.Subreddit;
import com.redditclone.entities.User;
import com.redditclone.services.AuthService;
import com.redditclone.services.SubredditService;
import com.redditclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
public class PostRequestToPost implements Converter<PostRequest, Post> {

    private final AuthService authService;
    private final UserService userService;
    private final SubredditService subredditService;

    @Override
    public Post convert(PostRequest postRequest) {
        User user = userService.findByUsername(authService.getCurrentUserAuthentication().getName());
        Subreddit subreddit = subredditService.findById(postRequest.getSubredditId());
        Post post =
                Post.builder()
                        .title(postRequest.getTitle())
                        .url("Whatever")
                        .description(postRequest.getDescription())
                        .voteCount(0)
                        .user(user)
                        .subreddit(subreddit)
                        .createdTime(Instant.now())
                        .build();
        return post;
    }
}
