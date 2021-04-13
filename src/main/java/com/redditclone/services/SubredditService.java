package com.redditclone.services;

import com.redditclone.entities.Subreddit;
import com.redditclone.exceptions.RedditException;
import com.redditclone.repositories.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;

    public List<Subreddit> findAll() {
        return subredditRepository.findAll();
    }

    public Subreddit findById(Long id) {
        return subredditRepository.findById(id).orElseThrow(()-> new RedditException("Subreddit not found"));
    }

    public Subreddit save(Subreddit subreddit) {
        subreddit.setCreatedTime(Instant.now());
        return subredditRepository.save(subreddit);
    }

    @Transactional
    public void deleteById(Long id) {
        subredditRepository.deleteById(id);
    }
}
