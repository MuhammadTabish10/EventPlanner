package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TagDto;
import com.EventPlanner.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.save(tagDto));
    }

    @GetMapping("/tag")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TagDto>> getAllTag() {
        List<TagDto> tagDtoList = tagService.getAll();
        return ResponseEntity.ok(tagDtoList);
    }

    @GetMapping("/tag/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedTag(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = tagService.getAllPaginatedTag(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
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
    public ResponseEntity<PaginationResponse> getAllTagByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = tagService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/tag/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/tag/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDto> updateTag(@PathVariable Long id, @Valid @RequestBody TagDto tagDto) {
        TagDto updatedTagDto = tagService.update(id, tagDto);
        return ResponseEntity.ok(updatedTagDto);
    }
}
