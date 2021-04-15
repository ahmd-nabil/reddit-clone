package com.redditclone.converters;

import com.redditclone.dto.VoteDto;
import com.redditclone.entities.Vote;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VoteToVoteDto implements Converter<Vote, VoteDto> {

    @Override
    public VoteDto convert(Vote vote) {
        VoteDto voteDto = VoteDto.builder()
                .id(vote.getId())
                .direction(vote.getType().getDirection())
                .postId(vote.getPost().getId())
                .username(vote.getUser().getUsername())
                .build();
        return voteDto;
    }
}
