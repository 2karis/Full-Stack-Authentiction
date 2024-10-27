package io.siliconsavannah.backend.controller;

import io.siliconsavannah.backend.dto.ContentDto;
import io.siliconsavannah.backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @GetMapping("/readall")
    public ResponseEntity<List<ContentDto>> getAllContents() {
        return ResponseEntity.ok(contentService.getAllContents());
    }

    @GetMapping("/mycontent")
    public ResponseEntity<List<ContentDto>> getContentsByUser() {
        return ResponseEntity.ok(contentService.getAllContentByAuthenticatedUser());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ContentDto> getContentById(@PathVariable Integer id) {
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
    public ResponseEntity<Void> deleteContent(@PathVariable int id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok().build();
    }
}
