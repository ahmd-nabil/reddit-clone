package com.redditclone.repositories;

import com.redditclone.entities.Post;
import com.redditclone.entities.User;
import com.redditclone.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByPost(Post post);
    Optional<Vote> findAllByPostAndUser(Post post, User user);
}
