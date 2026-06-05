package com.project.communication.platform.service;

import com.project.communication.platform.dto.MessageRequest;
import com.project.communication.platform.dto.MessageResponse;
import com.project.communication.platform.entity.Message;
import com.project.communication.platform.exception.MessageNotFoundException;
import com.project.communication.platform.repository.MessageRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public MessageResponse sendMessage(MessageRequest request) {
        Message message = Message.builder()
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .channelId(request.getChannelId())
                .content(request.getContent())
                .sentAt(LocalDateTime.now())
                .build();

        Message savedMessage = messageRepository.save(message);
        return mapToMessageResponse(savedMessage);
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(this::mapToMessageResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getMessagesBySender(Long senderId) {
        return messageRepository.findBySenderId(senderId).stream()
                .map(this::mapToMessageResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getMessagesByReceiver(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId).stream()
                .map(this::mapToMessageResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getMessagesByChannel(Long channelId) {
        return messageRepository.findByChannelId(channelId).stream()
                .map(this::mapToMessageResponse)
                .toList();
    }

    @Transactional
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new MessageNotFoundException("Message not found with id: " + id);
        }
        messageRepository.deleteById(id);
    }

    private MessageResponse mapToMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .channelId(message.getChannelId())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }
}
