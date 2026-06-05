package com.project.communication.platform.repository;

import com.project.communication.platform.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderId(Long senderId);

    List<Message> findByReceiverId(Long receiverId);

    List<Message> findByChannelId(Long channelId);
}
