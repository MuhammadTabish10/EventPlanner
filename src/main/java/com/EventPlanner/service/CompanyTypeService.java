package com.EventPlanner.service;

import com.EventPlanner.dto.CompanyTypeDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface CompanyTypeService {
    CompanyTypeDto save(CompanyTypeDto companyTypeDto);
    List<CompanyTypeDto> getAll();
    PaginationResponse getAllPaginatedCompanyType(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByType(String type, Integer pageNumber, Integer pageSize);
    CompanyTypeDto findById(Long id);
    CompanyTypeDto findByType(String type);
    void deleteById(Long id);
    CompanyTypeDto update(Long id, CompanyTypeDto companyTypeDto);
}
