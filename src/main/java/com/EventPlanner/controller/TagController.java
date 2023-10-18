package com.EventPlanner.controller;

import com.EventPlanner.dto.TagDto;
import com.EventPlanner.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tag")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.save(tagDto));
    }

    @GetMapping("/tag")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TagDto>> getAllTag() {
        List<TagDto> tagDtoList = tagService.getAll();
        return ResponseEntity.ok(tagDtoList);
    }

    @GetMapping("/tag/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id) {
        TagDto tagDto = tagService.findById(id);
        return ResponseEntity.ok(tagDto);
    }

    @GetMapping("/tag/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDto> getTagByName(@PathVariable String name) {
        TagDto tagDto = tagService.findByName(name);
        return ResponseEntity.ok(tagDto);
    }

    @GetMapping("/tag/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TagDto>> getAllTagByName(@PathVariable String name) {
        List<TagDto> tagDtoList = tagService.searchByName(name);
        return ResponseEntity.ok(tagDtoList);
    }

    @DeleteMapping("/tag/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/tag/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDto> updateTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        TagDto updatedTagDto = tagService.update(id, tagDto);
        return ResponseEntity.ok(updatedTagDto);
    }
}
