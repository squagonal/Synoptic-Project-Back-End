package com.programming.chrisg.synopticproject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.programming.chrisg.synopticproject.dto.TopicDto;
import com.programming.chrisg.synopticproject.exceptions.SpringRedditException;
import com.programming.chrisg.synopticproject.mapper.TopicMapper;
import com.programming.chrisg.synopticproject.model.Topic;
import com.programming.chrisg.synopticproject.repository.TopicRepository;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Transactional
    public TopicDto save(TopicDto topicDto) {
        Topic save = topicRepository.save(topicMapper.mapDtoToTopic(topicDto));
        topicDto.setId(save.getId());
        return topicDto;
    }

    @Transactional(readOnly = true)
    public List<TopicDto> getAll() {
        return topicRepository.findAll()
                .stream()
                .map(topicMapper::mapTopicToDto)
                .collect(toList());
    }

    public TopicDto getTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No topic found with ID - " + id));
        return topicMapper.mapTopicToDto(topic);
    }
}
