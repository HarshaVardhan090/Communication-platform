package com.project.communication.platform.service;

import com.project.communication.platform.dto.CommunityRequest;
import com.project.communication.platform.dto.CommunityResponse;
import com.project.communication.platform.entity.Community;
import com.project.communication.platform.exception.CommunityNotFoundException;
import com.project.communication.platform.repository.CommunityRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Transactional
    public CommunityResponse createCommunity(CommunityRequest request) {
        Community community = Community.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(request.getCreatedBy())
                .createdAt(LocalDateTime.now())
                .build();

        Community savedCommunity = communityRepository.save(community);
        return mapToCommunityResponse(savedCommunity);
    }

    @Transactional(readOnly = true)
    public List<CommunityResponse> getAllCommunities() {
        return communityRepository.findAll().stream()
                .map(this::mapToCommunityResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CommunityResponse getCommunityById(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new CommunityNotFoundException("Community not found with id: " + id));
        return mapToCommunityResponse(community);
    }

    @Transactional(readOnly = true)
    public List<CommunityResponse> getCommunitiesByUser(Long userId) {
        return communityRepository.findByCreatedBy(userId).stream()
                .map(this::mapToCommunityResponse)
                .toList();
    }

    @Transactional
    public void deleteCommunity(Long id) {
        if (!communityRepository.existsById(id)) {
            throw new CommunityNotFoundException("Community not found with id: " + id);
        }
        communityRepository.deleteById(id);
    }

    private CommunityResponse mapToCommunityResponse(Community community) {
        return CommunityResponse.builder()
                .id(community.getId())
                .name(community.getName())
                .description(community.getDescription())
                .createdBy(community.getCreatedBy())
                .createdAt(community.getCreatedAt())
                .build();
    }
}
