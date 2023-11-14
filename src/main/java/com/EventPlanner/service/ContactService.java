package com.EventPlanner.service;

import com.EventPlanner.dto.ContactDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface ContactService {
    ContactDto save(ContactDto contactDto);
    List<ContactDto> getAll();
    PaginationResponse getAllPaginatedContact(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByCustomer(String customer, Integer pageNumber, Integer pageSize);
    ContactDto findById(Long id);
    ContactDto findByCustomer(String customer);
    void deleteById(Long id);
    ContactDto update(Long id, ContactDto contactDto);
}
