package com.project.communication.platform.repository;

import com.project.communication.platform.entity.FileDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDetailsRepository extends JpaRepository<FileDetails, Long> {

    List<FileDetails> findByUploadedBy(Long uploadedBy);

    List<FileDetails> findByChannelId(Long channelId);
}
