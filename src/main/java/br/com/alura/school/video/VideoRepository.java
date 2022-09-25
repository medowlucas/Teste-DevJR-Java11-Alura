package br.com.alura.school.video;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByVideoAndSectionId(String video, Long SectionId);

    Optional<List<Video>> findAllBySectionId(Long sectionId);

    Optional<Video> findFirstByVideo(String video);
}
