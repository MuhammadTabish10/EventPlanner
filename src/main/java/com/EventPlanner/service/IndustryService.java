package com.EventPlanner.service;

import com.EventPlanner.dto.CurrencyDto;
import com.EventPlanner.dto.IndustryDto;

import java.util.List;

public interface IndustryService {
    IndustryDto save(IndustryDto industryDto);
    List<IndustryDto> getAll();
    IndustryDto findById(Long id);
    IndustryDto findByName(String name);
    List<IndustryDto> searchByName(String name);
    void deleteById(Long id);
    IndustryDto update(Long id, IndustryDto industryDto);
}
