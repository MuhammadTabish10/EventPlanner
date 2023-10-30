package com.EventPlanner.service;

import com.EventPlanner.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    CompanyDto save(CompanyDto companyDto);
    List<CompanyDto> getAll();
    CompanyDto findById(Long id);
    CompanyDto findByName(String name);
    List<CompanyDto> searchByName(String name);
    void deleteById(Long id);
    CompanyDto update(Long id, CompanyDto companyDto);
}
