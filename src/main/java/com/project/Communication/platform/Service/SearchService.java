package com.project.communication.platform.service;

import com.project.communication.platform.dto.SearchResponse;
import com.project.communication.platform.entity.Channel;
import com.project.communication.platform.entity.Community;
import com.project.communication.platform.entity.FileDetails;
import com.project.communication.platform.entity.Message;
import com.project.communication.platform.entity.User;
import com.project.communication.platform.repository.ChannelRepository;
import com.project.communication.platform.repository.CommunityRepository;
import com.project.communication.platform.repository.FileDetailsRepository;
import com.project.communication.platform.repository.MessageRepository;
import com.project.communication.platform.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SearchService {

    private static final String TYPE_USER = "USER";
    private static final String TYPE_COMMUNITY = "COMMUNITY";
    private static final String TYPE_CHANNEL = "CHANNEL";
    private static final String TYPE_MESSAGE = "MESSAGE";
    private static final String TYPE_FILE = "FILE";

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final FileDetailsRepository fileDetailsRepository;

    public SearchService(
            UserRepository userRepository,
            CommunityRepository communityRepository,
            ChannelRepository channelRepository,
            MessageRepository messageRepository,
            FileDetailsRepository fileDetailsRepository) {
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
        this.channelRepository = channelRepository;
        this.messageRepository = messageRepository;
        this.fileDetailsRepository = fileDetailsRepository;
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> searchUsers(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Collections.emptyList();
        }

        return userRepository.searchByUsernameOrEmail(keyword.trim()).stream()
                .map(this::mapUserToSearchResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> searchCommunities(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Collections.emptyList();
        }

        return communityRepository.findByNameContainingIgnoreCase(keyword.trim()).stream()
                .map(this::mapCommunityToSearchResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> searchChannels(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Collections.emptyList();
        }

        return channelRepository.findByNameContainingIgnoreCase(keyword.trim()).stream()
                .map(this::mapChannelToSearchResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> searchMessages(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Collections.emptyList();
        }

        return messageRepository.findByContentContainingIgnoreCase(keyword.trim()).stream()
                .map(this::mapMessageToSearchResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> searchFiles(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Collections.emptyList();
        }

        return fileDetailsRepository.searchByFileNameOrFileType(keyword.trim()).stream()
                .map(this::mapFileToSearchResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> globalSearch(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Collections.emptyList();
        }

        List<SearchResponse> results = new ArrayList<>();
        results.addAll(searchUsers(keyword));
        results.addAll(searchCommunities(keyword));
        results.addAll(searchChannels(keyword));
        results.addAll(searchMessages(keyword));
        results.addAll(searchFiles(keyword));
        return results;
    }

    private SearchResponse mapUserToSearchResponse(User user) {
        return SearchResponse.builder()
                .type(TYPE_USER)
                .id(user.getId())
                .title(user.getUsername())
                .description(user.getEmail())
                .build();
    }

    private SearchResponse mapCommunityToSearchResponse(Community community) {
        return SearchResponse.builder()
                .type(TYPE_COMMUNITY)
                .id(community.getId())
                .title(community.getName())
                .description(community.getDescription())
                .build();
    }

    private SearchResponse mapChannelToSearchResponse(Channel channel) {
        return SearchResponse.builder()
                .type(TYPE_CHANNEL)
                .id(channel.getId())
                .title(channel.getName())
                .description(channel.getDescription())
                .build();
    }

    private SearchResponse mapMessageToSearchResponse(Message message) {
        return SearchResponse.builder()
                .type(TYPE_MESSAGE)
                .id(message.getId())
                .title(message.getContent())
                .description("Sender ID: " + message.getSenderId())
                .build();
    }

    private SearchResponse mapFileToSearchResponse(FileDetails fileDetails) {
        return SearchResponse.builder()
                .type(TYPE_FILE)
                .id(fileDetails.getId())
                .title(fileDetails.getFileName())
                .description(fileDetails.getFileType())
                .build();
    }
}
