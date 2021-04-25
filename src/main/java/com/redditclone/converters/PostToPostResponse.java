package com.redditclone.converters;

import com.redditclone.dto.PostResponse;
import com.redditclone.entities.Post;
import lombok.AllArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@AllArgsConstructor
@Component
public class PostToPostResponse implements Converter<Post, PostResponse> {

    @Override
    public PostResponse convert(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .description(post.getDescription())
                .votesCount(post.getVoteCount())
                .username(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .commentsCount(post.getComments().size())
                .createdTime(post.getCreatedTime())
                .timeAgo(new PrettyTime().format(Date.from(post.getCreatedTime())))
                .build();
    }
}
