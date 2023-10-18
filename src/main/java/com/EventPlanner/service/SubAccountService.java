package com.EventPlanner.service;

import com.EventPlanner.dto.SubAccountDto;

import java.util.List;

public interface SubAccountService {
    SubAccountDto save(SubAccountDto subAccountDto);
    List<SubAccountDto> getAll();
    SubAccountDto findById(Long id);
    SubAccountDto findByName(String name);
    List<SubAccountDto> searchByName(String name);
    void deleteById(Long id);
    SubAccountDto update(Long id, SubAccountDto subAccountDto);
}
