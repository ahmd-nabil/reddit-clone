package com.redditclone.converters;

import com.redditclone.dto.PostResponse;
import com.redditclone.entities.Post;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PostToPostResponse implements Converter<Post, PostResponse> {

    @Override
    public PostResponse convert(Post post) {
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .description(post.getDescription())
                .voteCount(post.getVoteCount())
                .userId(post.getUser().getId())
                .subredditId(post.getSubreddit().getId())
                .build();
        return postResponse;
    }
}
