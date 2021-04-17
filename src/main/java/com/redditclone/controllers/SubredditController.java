package com.redditclone.controllers;

import com.redditclone.converters.SubredditDtoToSubreddit;
import com.redditclone.converters.SubredditToSubredditDto;
import com.redditclone.dto.SubredditDto;
import com.redditclone.entities.Subreddit;
import com.redditclone.services.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;
    private final SubredditToSubredditDto subredditToSubredditDto;
    private final SubredditDtoToSubreddit subredditDtoToSubreddit;

    @GetMapping
    public List<SubredditDto> getAllSubreddits() {
        return subredditService.findAll().stream().map(subredditToSubredditDto::convert).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public SubredditDto getSubredditById(@PathVariable Long id) {
        Subreddit subreddit = subredditService.findById(id);
        return subredditToSubredditDto.convert(subreddit);
    }

    @PostMapping
    public ResponseEntity<Void> saveSubreddit(@RequestBody SubredditDto subredditDto) {
        Subreddit subreddit = subredditDtoToSubreddit.convert(subredditDto);
        subredditService.save(subreddit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubreddit(@PathVariable Long id) {
        subredditService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{name}")
    public Subreddit getSubredditByName(@PathVariable String name) {
        return subredditService.findByName(name);
    }
}
