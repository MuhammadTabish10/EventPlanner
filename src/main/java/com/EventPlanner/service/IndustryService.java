package com.EventPlanner.service;

import com.EventPlanner.dto.IndustryDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface IndustryService {
    IndustryDto save(IndustryDto industryDto);
    List<IndustryDto> getAll();
    PaginationResponse getAllPaginatedIndustry(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    IndustryDto findById(Long id);
    IndustryDto findByName(String name);
    void deleteById(Long id);
    IndustryDto update(Long id, IndustryDto industryDto);
}
