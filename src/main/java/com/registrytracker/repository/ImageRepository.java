package com.registrytracker.repository;

import com.registrytracker.entity.DockerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<DockerImage, Long> {
}
