package com.programming.chrisg.synopticproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.programming.chrisg.synopticproject.dto.PostRequest;
import com.programming.chrisg.synopticproject.dto.PostResponse;
import com.programming.chrisg.synopticproject.service.PostService;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/search/")
@AllArgsConstructor
public class SearchController {

    private final PostService postService;

    @GetMapping("/{keyword}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String keyword) {
        return status(HttpStatus.OK).body(postService.getPostsByKeyword(keyword));
    }
}
