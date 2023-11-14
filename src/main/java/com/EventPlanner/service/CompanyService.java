package com.EventPlanner.service;

import com.EventPlanner.dto.CompanyDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface CompanyService {
    CompanyDto save(CompanyDto companyDto);
    List<CompanyDto> getAll();
    PaginationResponse getAllPaginatedCompany(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    CompanyDto findById(Long id);
    CompanyDto findByName(String name);
    void deleteById(Long id);
    CompanyDto update(Long id, CompanyDto companyDto);
}
