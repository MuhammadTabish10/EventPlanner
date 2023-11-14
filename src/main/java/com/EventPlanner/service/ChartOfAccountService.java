package com.EventPlanner.service;

import com.EventPlanner.dto.ChartOfAccountDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface ChartOfAccountService {
    ChartOfAccountDto save(ChartOfAccountDto chartOfAccountDto);
    List<ChartOfAccountDto> getAll();
    PaginationResponse getAllPaginatedChartOfAccount(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    ChartOfAccountDto findById(Long id);
    ChartOfAccountDto findByName(String name);
    void deleteById(Long id);
    ChartOfAccountDto update(Long id, ChartOfAccountDto chartOfAccountDto);
}
