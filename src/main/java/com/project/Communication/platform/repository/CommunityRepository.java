package com.project.communication.platform.repository;

import com.project.communication.platform.entity.Community;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findByCreatedBy(Long createdBy);

    List<Community> findByNameContainingIgnoreCase(String keyword);
}
