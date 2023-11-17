package com.EventPlanner.service;

import com.EventPlanner.dto.DynamicFeeDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface DynamicFeeService {
    DynamicFeeDto save(DynamicFeeDto dynamicFeeDto);
    List<DynamicFeeDto> getAll();
    PaginationResponse getAllPaginatedDynamicFee(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByTicketName(String name, Integer pageNumber, Integer pageSize);
    DynamicFeeDto findById(Long id);
    DynamicFeeDto findByTicketName(String name);
    void deleteById(Long id);
    DynamicFeeDto update(Long id, DynamicFeeDto dynamicFeeDto);
}
