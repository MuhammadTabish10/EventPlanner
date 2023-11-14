package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.ProvinceDto;

import java.util.List;

public interface ProvinceService {
    ProvinceDto save(ProvinceDto provinceDto);
    List<ProvinceDto> getAll();
    PaginationResponse getAllPaginatedProvince(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    ProvinceDto findById(Long id);
    ProvinceDto findByName(String name);
    void deleteById(Long id);
    ProvinceDto update(Long id, ProvinceDto provinceDto);
}
