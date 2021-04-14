package com.redditclone.converters;

import com.redditclone.dto.SubredditDto;
import com.redditclone.entities.Subreddit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubredditDtoToSubreddit implements Converter<SubredditDto, Subreddit> {
    @Override
    public Subreddit convert(SubredditDto subredditDto) {
        Subreddit subreddit = Subreddit.builder()
                .id(subredditDto.getId())
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
        return subreddit;
    }
}
