package com.redditclone.services;

import com.redditclone.converters.PostRequestToPost;
import com.redditclone.converters.PostToPostResponse;
import com.redditclone.dto.PostRequest;
import com.redditclone.entities.Post;
import com.redditclone.entities.Subreddit;
import com.redditclone.entities.User;
import com.redditclone.exceptions.RedditException;
import com.redditclone.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final UserService userService;
    private final SubredditService subredditService;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final PostRequestToPost postRequestToPost;
    private final PostToPostResponse postToPostResponse;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(()->new RedditException("Post not found!"));
    }

    public Post save(PostRequest postRequest) {
        Post post = postRequestToPost.convert(postRequest);
        return postRepository.save(post);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void deleteById(Long id) {
        User currentUser = userService.findByUsername(authService.getCurrentUserAuthentication().getName());
        Post post = findById(id);
        if(currentUser.getId() != post.getUser().getId())
            throw new RedditException("Something went wrong. can't delete others posts");
        postRepository.deleteById(id);
    }

    public List<Post> findAllByUsername(String username) {
        User user = userService.findByUsername(username);
        return postRepository.findAllByUser(user);
    }

    public List<Post> findAllBySubredditName(String name) {
        Subreddit subreddit = subredditService.findByName(name);
        return postRepository.findAllBySubreddit(subreddit);
    }

    public List<Post> findAllBySubredditId(Long subredditId) {
        Subreddit subreddit = subredditService.findById(subredditId);
        return postRepository.findAllBySubreddit(subreddit);
    }
}
