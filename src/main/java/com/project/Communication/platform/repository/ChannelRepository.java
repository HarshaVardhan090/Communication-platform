package com.project.communication.platform.repository;

import com.project.communication.platform.entity.Channel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    List<Channel> findByCommunityId(Long communityId);

    List<Channel> findByNameContainingIgnoreCase(String keyword);
}
