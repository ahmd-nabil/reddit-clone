package com.redditclone.controllers;

import com.redditclone.converters.VoteToVoteDto;
import com.redditclone.dto.VoteDto;
import com.redditclone.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;
    private final VoteToVoteDto voteToVoteDto;

    @GetMapping("/{postId}")
    public List<VoteDto> getVotesByPost(@PathVariable Long postId) {
        return voteService.findVotesByPost(postId).stream().map(voteToVoteDto::convert).collect(Collectors.toList());
    }

    @PostMapping("/{postId}")
    public ResponseEntity addVote(@PathVariable Long postId, @RequestBody VoteDto voteDto) {
        voteService.save(postId, voteDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
