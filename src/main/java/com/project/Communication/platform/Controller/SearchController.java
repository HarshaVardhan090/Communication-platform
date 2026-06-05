package com.project.communication.platform.controller;

import com.project.communication.platform.dto.SearchResponse;
import com.project.communication.platform.service.SearchService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<SearchResponse>> searchUsers(@RequestParam String keyword) {
        List<SearchResponse> results = searchService.searchUsers(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/communities")
    public ResponseEntity<List<SearchResponse>> searchCommunities(@RequestParam String keyword) {
        List<SearchResponse> results = searchService.searchCommunities(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/channels")
    public ResponseEntity<List<SearchResponse>> searchChannels(@RequestParam String keyword) {
        List<SearchResponse> results = searchService.searchChannels(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<SearchResponse>> searchMessages(@RequestParam String keyword) {
        List<SearchResponse> results = searchService.searchMessages(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/files")
    public ResponseEntity<List<SearchResponse>> searchFiles(@RequestParam String keyword) {
        List<SearchResponse> results = searchService.searchFiles(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/global")
    public ResponseEntity<List<SearchResponse>> globalSearch(@RequestParam String keyword) {
        List<SearchResponse> results = searchService.globalSearch(keyword);
        return ResponseEntity.ok(results);
    }
}
