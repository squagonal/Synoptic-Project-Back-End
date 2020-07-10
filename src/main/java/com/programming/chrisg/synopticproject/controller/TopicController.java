package com.programming.chrisg.synopticproject.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.programming.chrisg.synopticproject.dto.TopicDto;
import com.programming.chrisg.synopticproject.service.TopicService;

@RestController
@RequestMapping("/api/Topic")
@AllArgsConstructor
@Slf4j
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicDto> createTopic(@RequestBody TopicDto topicDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(topicService.save(topicDto));
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllTopics() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topicService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topicService.getTopic(id));
    }
}
