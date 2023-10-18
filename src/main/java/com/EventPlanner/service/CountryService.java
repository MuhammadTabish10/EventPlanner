package com.EventPlanner.service;

import com.EventPlanner.dto.CountryDto;

import java.util.List;

public interface CountryService {
    CountryDto save(CountryDto countryDto);
    List<CountryDto> getAll();
    CountryDto findById(Long id);
    CountryDto findByName(String name);
    List<CountryDto> searchByName(String name);
    void deleteById(Long id);
    CountryDto update(Long id, CountryDto countryDto);
}
