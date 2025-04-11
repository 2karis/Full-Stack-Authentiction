package io.siliconsavannah.backend.controller;

import io.siliconsavannah.backend.dto.ContentDto;
import io.siliconsavannah.backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @GetMapping()
    public ResponseEntity<Page<ContentDto>> getContents(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam( value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(contentService.getAllContents(page, size));
    }

    @GetMapping("/mycontent")
    public ResponseEntity<List<ContentDto>> getContentsByUser() {
        return ResponseEntity.ok(contentService.getAllContentByAuthenticatedUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDto> getContentById(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<ContentDto> createContent(@RequestBody ContentDto contentDto) {
        return ResponseEntity.ok(contentService.createContent(contentDto));
    }
    @PutMapping("/update")
    public ResponseEntity<ContentDto> updateContent(@RequestBody ContentDto contentDto) {
        return ResponseEntity.ok(contentService.updateContent(contentDto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam Long id, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(contentService.uploadImage(id, file));
    }

    @GetMapping(path = "/image/{filename}")
    public byte[] getImage(@PathVariable String filename) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources/static/images/" + filename));
    }
}
