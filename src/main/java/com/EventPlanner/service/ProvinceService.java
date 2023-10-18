package com.EventPlanner.service;

import com.EventPlanner.dto.ProvinceDto;

import java.util.List;

public interface ProvinceService {
    ProvinceDto save(ProvinceDto provinceDto);
    List<ProvinceDto> getAll();
    ProvinceDto findById(Long id);
    ProvinceDto findByName(String name);
    List<ProvinceDto> searchByName(String name);
    void deleteById(Long id);
    ProvinceDto update(Long id, ProvinceDto provinceDto);
}
