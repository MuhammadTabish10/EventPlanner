package com.EventPlanner.service;

import com.EventPlanner.dto.DiscountRuleDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface DiscountRuleService {
    DiscountRuleDto save(DiscountRuleDto discountRuleDto);
    List<DiscountRuleDto> getAll();
    PaginationResponse getAllPaginatedDiscountRule(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByDiscountCode(String discountCode, Integer pageNumber, Integer pageSize);
    DiscountRuleDto findById(Long id);
    void deleteById(Long id);
    DiscountRuleDto update(Long id, DiscountRuleDto discountRuleDto);
}
