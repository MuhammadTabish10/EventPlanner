package com.EventPlanner.service;

import com.EventPlanner.dto.ContactDto;

import java.util.List;

public interface ContactService {
    ContactDto save(ContactDto contactDto);
    List<ContactDto> getAll();
    ContactDto findById(Long id);
    ContactDto findByCustomer(String customer);
    List<ContactDto> searchByCustomer(String customer);
    void deleteById(Long id);
    ContactDto update(Long id, ContactDto contactDto);
}
