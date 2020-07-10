package com.programming.chrisg.synopticproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import com.programming.chrisg.synopticproject.dto.TopicDto;
import com.programming.chrisg.synopticproject.model.Post;
import com.programming.chrisg.synopticproject.model.Topic;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(topic.getPosts()))")
    TopicDto mapTopicToDto(Topic topic);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Topic mapDtoToTopic(TopicDto topicDto);
}
