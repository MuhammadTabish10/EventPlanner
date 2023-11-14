package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto save(TagDto tagDto);
    List<TagDto> getAll();
    PaginationResponse getAllPaginatedTag(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    TagDto findById(Long id);
    TagDto findByName(String name);
    void deleteById(Long id);
    TagDto update(Long id, TagDto tagDto);
}
