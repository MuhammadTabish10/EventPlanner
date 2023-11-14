package com.EventPlanner.service;

import com.EventPlanner.dto.CountryDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface CountryService {
    CountryDto save(CountryDto countryDto);
    List<CountryDto> getAll();
    PaginationResponse getAllPaginatedCountry(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    CountryDto findById(Long id);
    CountryDto findByName(String name);
    void deleteById(Long id);
    CountryDto update(Long id, CountryDto countryDto);
}
