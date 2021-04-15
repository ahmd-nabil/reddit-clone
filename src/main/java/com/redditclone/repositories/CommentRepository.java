package com.redditclone.repositories;

import com.redditclone.entities.Comment;
import com.redditclone.entities.Post;
import com.redditclone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser(User user);
    List<Comment> findAllByPost(Post post);
}
