package com.redditclone.converters;

import com.redditclone.dto.SubredditDto;
import com.redditclone.entities.Subreddit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubredditToSubredditDto implements Converter<Subreddit, SubredditDto> {
    @Override
    public SubredditDto convert(Subreddit subreddit) {
        SubredditDto subredditDto = SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
        return subredditDto;
    }
}
