package com.redditclone.converters;

import com.redditclone.dto.VoteDto;
import com.redditclone.entities.Post;
import com.redditclone.entities.User;
import com.redditclone.entities.Vote;
import com.redditclone.entities.VoteType;
import com.redditclone.services.AuthService;
import com.redditclone.services.PostService;
import com.redditclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoteDtoToVote implements Converter<VoteDto, Vote> {

    private final PostService postService;
    private final AuthService authService;
    private final UserService userservice;

    @Override
    public Vote convert(VoteDto voteDto) {
        Post post = postService.findById(voteDto.getPostId());
        User user = userservice.findByUsername(authService.getCurrentUserAuthentication().getName());
        Vote vote = Vote.builder()
                .id(voteDto.getId())
                .type(voteDto.getDirection() >= 1? VoteType.UPVOTE: VoteType.DOWNVOTE)
                .post(post)
                .user(user)
                .build();
        return vote;
    }
}
