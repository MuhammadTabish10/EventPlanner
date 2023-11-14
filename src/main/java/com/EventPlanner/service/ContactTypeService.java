package com.EventPlanner.service;

import com.EventPlanner.dto.ContactTypeDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface ContactTypeService {
    ContactTypeDto save(ContactTypeDto contactTypeDto);
    List<ContactTypeDto> getAll();
    PaginationResponse getAllPaginatedContactType(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByType(String type, Integer pageNumber, Integer pageSize);
    ContactTypeDto findById(Long id);
    ContactTypeDto findByType(String type);
    void deleteById(Long id);
    ContactTypeDto update(Long id, ContactTypeDto contactTypeDto);
}
