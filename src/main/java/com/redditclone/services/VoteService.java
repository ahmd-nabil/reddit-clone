package com.redditclone.services;

import com.redditclone.converters.VoteDtoToVote;
import com.redditclone.dto.VoteDto;
import com.redditclone.entities.Post;
import com.redditclone.entities.User;
import com.redditclone.entities.Vote;
import com.redditclone.repositories.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostService postService;
    private final AuthService authService;
    private final UserService userService;
    private final VoteDtoToVote voteDtoToVote;

    public List<Vote> findVotesByPost(Long postId) {
        Post post = postService.findById(postId);
        return voteRepository.findAllByPost(post);
    }

    public Vote findVoteByPostAndCurrentUser(Long postId) {
        Post post = postService.findById(postId);
        User user = userService.findByUsername(authService.getCurrentUserAuthentication().getName());
        Vote vote = voteRepository.findAllByPostAndUser(post, user).orElse(null);
        return vote;
    }


    public Vote findVoteByPostAndCurrentUser(Post post) {
        User user = userService.findByUsername(authService.getCurrentUserAuthentication().getName());
        Vote vote = voteRepository.findAllByPostAndUser(post, user).orElse(null);
        return vote;
    }

    public Vote save(Long postId, VoteDto voteDto) {
        voteDto.setUsername(authService.getCurrentUserAuthentication().getName());
        voteDto.setPostId(postId);
        // Check previous vote and negate it, then add new vote
        Post post = postService.findById(postId);
        Vote prevVote = findVoteByPostAndCurrentUser(post);
        if(prevVote != null) {
            post.setVoteCount(post.getVoteCount() - prevVote.getType().getDirection());
            deleteVote(prevVote);
            postService.save(post);
            if(voteDto.getDirection() == prevVote.getType().getDirection())
                return null;
        }
        Vote vote = voteDtoToVote.convert(voteDto);
        post.setVoteCount(post.getVoteCount() + vote.getType().getDirection());
        return voteRepository.save(vote);
    }

    public void deleteById(Long id) {
        voteRepository.deleteById(id);
    }

    public void deleteVote(Vote vote) {
        voteRepository.delete(vote);
    }
}
