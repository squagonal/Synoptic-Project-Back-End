package com.programming.chrisg.synopticproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.programming.chrisg.synopticproject.model.Post;
import com.programming.chrisg.synopticproject.model.User;
import com.programming.chrisg.synopticproject.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
