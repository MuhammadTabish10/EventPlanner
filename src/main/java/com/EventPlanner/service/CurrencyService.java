package com.EventPlanner.service;

import com.EventPlanner.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {
    CurrencyDto save(CurrencyDto currencyDto);
    List<CurrencyDto> getAll();
    CurrencyDto findById(Long id);
    CurrencyDto findByName(String name);
    List<CurrencyDto> searchByName(String name);
    void deleteById(Long id);
    CurrencyDto update(Long id, CurrencyDto currencyDto);
}
