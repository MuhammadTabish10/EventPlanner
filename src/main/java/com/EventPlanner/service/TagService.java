package com.EventPlanner.service;

import com.EventPlanner.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto save(TagDto tagDto);
    List<TagDto> getAll();
    TagDto findById(Long id);
    TagDto findByName(String name);
    List<TagDto> searchByName(String name);
    void deleteById(Long id);
    TagDto update(Long id, TagDto tagDto);
}
