package com.registrytracker.controller;

import com.registrytracker.dto.ImageDTO;
import com.registrytracker.entity.DockerImage;
import com.registrytracker.exception.ResourceNotFoundException;
import com.registrytracker.repository.ImageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    
    private final ImageRepository imageRepository;
    
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    
    @GetMapping
    public List<ImageDTO> getAllImages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return imageRepository.findAll(PageRequest.of(page, size))
            .getContent().stream()
            .map(this::toDTO)
            .toList();
    }
    
    @GetMapping("/{id}")
    public ImageDTO getImageById(@PathVariable Long id) {
        return toDTO(imageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Docker image", id)));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImageDTO createImage(@RequestBody ImageDTO imageDTO) {
        return toDTO(imageRepository.save(toEntity(imageDTO)));
    }
    
    @PutMapping("/{id}")
    public ImageDTO updateImage(@PathVariable Long id, @RequestBody ImageDTO imageDTO) {
        if (!imageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Docker image", id);
        }
        DockerImage image = toEntity(imageDTO);
        image.setId(id);
        return toDTO(imageRepository.save(image));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long id) {
        if (!imageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Docker image", id);
        }
        imageRepository.deleteById(id);
    }
    
    // DTO Mapping
    private ImageDTO toDTO(DockerImage image) {
        return new ImageDTO(
            image.getId(),
            image.getImageName(),
            image.getTag(),
            image.getRegistry(),
            image.getCreatedDate()
        );
    }
    
    private DockerImage toEntity(ImageDTO dto) {
        DockerImage image = new DockerImage();
        image.setImageName(dto.getImageName());
        image.setTag(dto.getTag());
        image.setRegistry(dto.getRegistry());
        image.setCreatedDate(dto.getCreatedDate());
        return image;
    }
}
