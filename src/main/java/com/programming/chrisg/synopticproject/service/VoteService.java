package com.programming.chrisg.synopticproject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.programming.chrisg.synopticproject.model.VoteType.UPVOTE;

import java.util.Optional;


import com.programming.chrisg.synopticproject.dto.VoteDto;
import com.programming.chrisg.synopticproject.exceptions.PostNotFoundException;
import com.programming.chrisg.synopticproject.exceptions.SpringRedditException;
import com.programming.chrisg.synopticproject.model.Post;
import com.programming.chrisg.synopticproject.model.User;
import com.programming.chrisg.synopticproject.model.Vote;
import com.programming.chrisg.synopticproject.repository.PostRepository;
import com.programming.chrisg.synopticproject.repository.UserRepository;
import com.programming.chrisg.synopticproject.repository.VoteRepository;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
            //post.getUser().setVoteCount(post.getUser().getVoteCount()+1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
            //post.getUser().setVoteCount(post.getUser().getVoteCount()-1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
    public Integer getUserTotalVotes(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return user.getVoteCount();
                
                
    }
}
