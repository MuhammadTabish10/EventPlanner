package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SubAccountDto;
import com.EventPlanner.dto.TagDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.SubAccount;
import com.EventPlanner.model.Tag;
import com.EventPlanner.repository.TagRepository;
import com.EventPlanner.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public TagDto save(TagDto tagDto) {
        Tag tag = toEntity(tagDto);
        tag.setStatus(true);
        Tag createdTag = tagRepository.save(tag);
        return toDto(createdTag);
    }

    @Override
    public List<TagDto> getAll() {
        List<Tag> tagList = tagRepository.findAllInDesOrderByIdAndStatus();
        List<TagDto> tagDtoList = new ArrayList<>();

        for (Tag tag : tagList) {
            TagDto tagDto = toDto(tag);
            tagDtoList.add(tagDto);
        }
        return tagDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedTag(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Tag> pageTag = tagRepository.findAllInDesOrderByIdAndStatus(page);
        List<Tag> tagList = pageTag.getContent();

        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDto tagDto = toDto(tag);
            tagDtoList.add(tagDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(tagDtoList);
        paginationResponse.setPageNumber(pageTag.getNumber());
        paginationResponse.setPageSize(pageTag.getSize());
        paginationResponse.setTotalElements(pageTag.getNumberOfElements());
        paginationResponse.setTotalPages(pageTag.getTotalPages());
        paginationResponse.setLastPage(pageTag.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Tag> pageTag = tagRepository.findTagByName(name,page);
        List<Tag> tagList = pageTag.getContent();

        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDto tagDto = toDto(tag);
            tagDtoList.add(tagDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(tagDtoList);
        paginationResponse.setPageNumber(pageTag.getNumber());
        paginationResponse.setPageSize(pageTag.getSize());
        paginationResponse.setTotalElements(pageTag.getNumberOfElements());
        paginationResponse.setTotalPages(pageTag.getTotalPages());
        paginationResponse.setLastPage(pageTag.isLast());

        return paginationResponse;
    }

    @Override
    public TagDto findById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Tag not found for id => %d", id)));
        return toDto(tag);
    }

    @Override
    public TagDto findByName(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Tag not found for name => %s", name)));
        return toDto(tag);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Tag not found for id => %d", id)));
        tagRepository.setStatusInactive(tag.getId());
    }

    @Override
    @Transactional
    public TagDto update(Long id, TagDto tagDto) {
        Tag existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Tag not found for id => %d", id)));

        existingTag.setName(tagDto.getName());

        Tag updatedTag = tagRepository.save(existingTag);
        return toDto(updatedTag);
    }

    public TagDto toDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .status(tag.getStatus())
                .build();
    }

    public Tag toEntity(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .status(tagDto.getStatus())
                .build();
    }
}
