package com.project.communication.platform.controller;

import com.project.communication.platform.dto.ChannelRequest;
import com.project.communication.platform.dto.ChannelResponse;
import com.project.communication.platform.service.ChannelService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping
    public ResponseEntity<ChannelResponse> createChannel(@Valid @RequestBody ChannelRequest request) {
        ChannelResponse response = channelService.createChannel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChannelResponse>> getAllChannels() {
        List<ChannelResponse> channels = channelService.getAllChannels();
        return ResponseEntity.ok(channels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelResponse> getChannelById(@PathVariable Long id) {
        ChannelResponse response = channelService.getChannelById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<ChannelResponse>> getChannelsByCommunity(@PathVariable Long communityId) {
        List<ChannelResponse> channels = channelService.getChannelsByCommunity(communityId);
        return ResponseEntity.ok(channels);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        channelService.deleteChannel(id);
        return ResponseEntity.noContent().build();
    }
}
