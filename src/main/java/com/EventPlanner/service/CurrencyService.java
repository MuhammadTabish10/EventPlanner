package com.EventPlanner.service;

import com.EventPlanner.dto.CurrencyDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface CurrencyService {
    CurrencyDto save(CurrencyDto currencyDto);
    List<CurrencyDto> getAll();
    PaginationResponse getAllPaginatedCurrency(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    CurrencyDto findById(Long id);
    CurrencyDto findByName(String name);
    void deleteById(Long id);
    CurrencyDto update(Long id, CurrencyDto currencyDto);
}
