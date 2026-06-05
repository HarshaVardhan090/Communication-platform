package com.project.communication.platform.service;

import com.project.communication.platform.dto.ChannelRequest;
import com.project.communication.platform.dto.ChannelResponse;
import com.project.communication.platform.entity.Channel;
import com.project.communication.platform.exception.ChannelNotFoundException;
import com.project.communication.platform.repository.ChannelRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional
    public ChannelResponse createChannel(ChannelRequest request) {
        Channel channel = Channel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .communityId(request.getCommunityId())
                .createdAt(LocalDateTime.now())
                .build();

        Channel savedChannel = channelRepository.save(channel);
        return mapToChannelResponse(savedChannel);
    }

    @Transactional(readOnly = true)
    public List<ChannelResponse> getAllChannels() {
        return channelRepository.findAll().stream()
                .map(this::mapToChannelResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ChannelResponse getChannelById(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ChannelNotFoundException("Channel not found with id: " + id));
        return mapToChannelResponse(channel);
    }

    @Transactional(readOnly = true)
    public List<ChannelResponse> getChannelsByCommunity(Long communityId) {
        return channelRepository.findByCommunityId(communityId).stream()
                .map(this::mapToChannelResponse)
                .toList();
    }

    @Transactional
    public void deleteChannel(Long id) {
        if (!channelRepository.existsById(id)) {
            throw new ChannelNotFoundException("Channel not found with id: " + id);
        }
        channelRepository.deleteById(id);
    }

    private ChannelResponse mapToChannelResponse(Channel channel) {
        return ChannelResponse.builder()
                .id(channel.getId())
                .name(channel.getName())
                .description(channel.getDescription())
                .communityId(channel.getCommunityId())
                .createdAt(channel.getCreatedAt())
                .build();
    }
}
