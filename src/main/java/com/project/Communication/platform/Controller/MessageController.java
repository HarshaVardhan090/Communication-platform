package com.project.communication.platform.controller;

import com.project.communication.platform.dto.MessageRequest;
import com.project.communication.platform.dto.MessageResponse;
import com.project.communication.platform.service.MessageService;
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
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest request) {
        MessageResponse response = messageService.sendMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getAllMessages() {
        List<MessageResponse> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<MessageResponse>> getMessagesBySender(@PathVariable Long senderId) {
        List<MessageResponse> messages = messageService.getMessagesBySender(senderId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByReceiver(@PathVariable Long receiverId) {
        List<MessageResponse> messages = messageService.getMessagesByReceiver(receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByChannel(@PathVariable Long channelId) {
        List<MessageResponse> messages = messageService.getMessagesByChannel(channelId);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
