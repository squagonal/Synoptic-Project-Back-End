package com.programming.chrisg.synopticproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.programming.chrisg.synopticproject.model.Post;
import com.programming.chrisg.synopticproject.model.Topic;
import com.programming.chrisg.synopticproject.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTopic(Topic topic);
    List<Post> findByUser(User user);
    @Query(value = "SELECT * FROM Post ORDER BY vote_Count DESC",
            nativeQuery = true)
    List<Post> findAllByOrderByVoteCountDesc();
    @Query(value = "SELECT * FROM Post ORDER BY created_date DESC",
            nativeQuery = true)
    List<Post> findAllByOrderByCreatedDateDesc();
    @Query(value = "SELECT * FROM Post WHERE post_name CONTAINS ?1",
    nativeQuery = true)
List<Post> findByKeyword(String keyword);
}
