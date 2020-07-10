package com.programming.chrisg.synopticproject.controller;

import lombok.AllArgsConstructor;

import com.programming.chrisg.synopticproject.dto.VoteDto;
import com.programming.chrisg.synopticproject.service.VoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/totalvotes/{userName}")
    public ResponseEntity<Integer>getUserTotalVotes(@PathVariable String username){
        return ResponseEntity.status(OK)
                .body(voteService.getUserTotalVotes(username));
    }

}