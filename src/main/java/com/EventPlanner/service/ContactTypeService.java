package com.EventPlanner.service;

import com.EventPlanner.dto.ContactTypeDto;

import java.util.List;

public interface ContactTypeService {
    ContactTypeDto save(ContactTypeDto contactTypeDto);
    List<ContactTypeDto> getAll();
    ContactTypeDto findById(Long id);
    ContactTypeDto findByType(String type);
    List<ContactTypeDto> searchByType(String type);
    void deleteById(Long id);
    ContactTypeDto update(Long id, ContactTypeDto contactTypeDto);
}
