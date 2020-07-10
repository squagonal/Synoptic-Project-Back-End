package com.programming.chrisg.synopticproject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.programming.chrisg.synopticproject.dto.PostRequest;
import com.programming.chrisg.synopticproject.dto.PostResponse;
import com.programming.chrisg.synopticproject.exceptions.PostNotFoundException;
import com.programming.chrisg.synopticproject.exceptions.TopicNotFoundException;
import com.programming.chrisg.synopticproject.mapper.PostMapper;
import com.programming.chrisg.synopticproject.model.Post;
import com.programming.chrisg.synopticproject.model.Topic;
import com.programming.chrisg.synopticproject.model.User;
import com.programming.chrisg.synopticproject.repository.PostRepository;
import com.programming.chrisg.synopticproject.repository.TopicRepository;
import com.programming.chrisg.synopticproject.repository.UserRepository;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Topic topic = topicRepository.findByName(postRequest.getTopicName())
                .orElseThrow(() -> new TopicNotFoundException(postRequest.getTopicName()));
        postRepository.save(postMapper.map(postRequest, topic, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByVoteCountDesc()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId.toString()));
        List<Post> posts = postRepository.findAllByTopic(topic);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByKeyword(String keyword) {
        return postRepository.findByKeyword(keyword)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
