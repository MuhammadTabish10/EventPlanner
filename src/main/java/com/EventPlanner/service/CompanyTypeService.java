package com.EventPlanner.service;

import com.EventPlanner.dto.CompanyTypeDto;

import java.util.List;

public interface CompanyTypeService {
    CompanyTypeDto save(CompanyTypeDto companyTypeDto);
    List<CompanyTypeDto> getAll();
    CompanyTypeDto findById(Long id);
    CompanyTypeDto findByType(String type);
    List<CompanyTypeDto> searchByType(String type);
    void deleteById(Long id);
    CompanyTypeDto update(Long id, CompanyTypeDto companyTypeDto);
}
