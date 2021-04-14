package com.redditclone.repositories;

import com.redditclone.entities.Post;
import com.redditclone.entities.Subreddit;
import com.redditclone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
    List<Post> findAllBySubreddit(Subreddit subreddit);
}
