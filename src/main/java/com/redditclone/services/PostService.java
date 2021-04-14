package com.redditclone.services;

import com.redditclone.converters.PostRequestToPost;
import com.redditclone.converters.PostToPostResponse;
import com.redditclone.dto.PostRequest;
import com.redditclone.dto.PostResponse;
import com.redditclone.entities.Post;
import com.redditclone.entities.Subreddit;
import com.redditclone.entities.User;
import com.redditclone.exceptions.RedditException;
import com.redditclone.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class PostService {

    private final UserService userService;
    private final SubredditService subredditService;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final PostRequestToPost postRequestToPost;
    private final PostToPostResponse postToPostResponse;

    public List<PostResponse> findAll() {
        return postRepository.findAll().stream().map(post -> postToPostResponse.convert(post)).collect(Collectors.toList());
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new RedditException("Post Not Found!"));
        PostResponse postResponse = postToPostResponse.convert(post);
        return postResponse;
    }

    public PostResponse save(PostRequest postRequest) {
        Post post = postRequestToPost.convert(postRequest);
        post = postRepository.save(post);
        PostResponse postResponse = postToPostResponse.convert(post);
        return postResponse;
    }

    public void deleteById(Long id) {
        User currentUser = userService.findByUsername(authService.getCurrentUserAuthentication().getName());
        PostResponse postResponse = findById(id);
        if(currentUser.getId() != postResponse.getUserId())
            throw new RedditException("Something went wrong. can't delete others posts");
        postRepository.deleteById(id);
    }

    public List<PostResponse> findAllByUsername(String username) {
        User user = userService.findByUsername(username);
        List<PostResponse> postResponses = postRepository.findAllByUser(user).stream().map(post -> postToPostResponse.convert(post)).collect(Collectors.toList());
        return postResponses;
    }

    public List<PostResponse> findAllBySubredditName(String name) {
        Subreddit subreddit = subredditService.findByName(name);
        List<PostResponse> postResponses =  postRepository.findAllBySubreddit(subreddit).stream().map(post -> postToPostResponse.convert(post)).collect(Collectors.toList());
        return postResponses;
    }

    public List<PostResponse> findAllBySubredditId(Long subredditId) {
        Subreddit subreddit = subredditService.findById(subredditId);
        List<PostResponse> postResponses =  postRepository.findAllBySubreddit(subreddit).stream().map(post -> postToPostResponse.convert(post)).collect(Collectors.toList());
        return postResponses;
    }
}
