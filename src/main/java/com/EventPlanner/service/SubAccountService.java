package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SubAccountDto;

import java.util.List;

public interface SubAccountService {
    SubAccountDto save(SubAccountDto subAccountDto);
    List<SubAccountDto> getAll();
    PaginationResponse getAllPaginatedSubAccount(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    SubAccountDto findById(Long id);
    SubAccountDto findByName(String name);
    void deleteById(Long id);
    SubAccountDto update(Long id, SubAccountDto subAccountDto);
}
