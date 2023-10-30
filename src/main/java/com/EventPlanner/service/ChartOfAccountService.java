package com.EventPlanner.service;

import com.EventPlanner.dto.ChartOfAccountDto;

import java.util.List;

public interface ChartOfAccountService {
    ChartOfAccountDto save(ChartOfAccountDto chartOfAccountDto);
    List<ChartOfAccountDto> getAll();
    ChartOfAccountDto findById(Long id);
    ChartOfAccountDto findByName(String name);
    List<ChartOfAccountDto> searchByName(String name);
    void deleteById(Long id);
    ChartOfAccountDto update(Long id, ChartOfAccountDto chartOfAccountDto);
}
