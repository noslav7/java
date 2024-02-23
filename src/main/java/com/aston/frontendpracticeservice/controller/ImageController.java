package com.aston.frontendpracticeservice.controller;

import com.aston.frontendpracticeservice.domain.response.SimpleMessage;
import com.aston.frontendpracticeservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/frontend-practice/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<SimpleMessage> uploadImage(@RequestParam("image") MultipartFile multipartFile) {
        SimpleMessage simpleMessage = imageService.uploadImage(multipartFile);
        return ResponseEntity.ok(simpleMessage);
    }

    @GetMapping
    public ResponseEntity<Resource> downloadImage() {
        ByteArrayResource resource = imageService.downloadImage();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
