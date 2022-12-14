package br.com.alura.school.video;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    
    Optional<Video> findFirstByVideoAndSectionId(String video, Long SectionId);

    Optional<List<Video>> findAllBySectionId(Long sectionId);

    Optional<Video> findFirstByVideo(String video);
}
