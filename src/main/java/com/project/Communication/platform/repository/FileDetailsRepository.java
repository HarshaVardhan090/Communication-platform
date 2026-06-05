package com.project.communication.platform.repository;

import com.project.communication.platform.entity.FileDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDetailsRepository extends JpaRepository<FileDetails, Long> {

    List<FileDetails> findByUploadedBy(Long uploadedBy);

    List<FileDetails> findByChannelId(Long channelId);

    @Query("SELECT f FROM FileDetails f WHERE LOWER(f.fileName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(f.fileType) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<FileDetails> searchByFileNameOrFileType(@Param("keyword") String keyword);
}
