package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.StandardPriceDto;

import java.util.List;

public interface StandardPriceService {
    StandardPriceDto save(StandardPriceDto standardPriceDto);
    List<StandardPriceDto> getAll();
    PaginationResponse getAllPaginatedStandardPrice(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByTicketName(String ticketName, Integer pageNumber, Integer pageSize);
    StandardPriceDto findById(Long id);
    StandardPriceDto findByTicketName(String ticketName);
    void deleteById(Long id);
    StandardPriceDto update(Long id, StandardPriceDto standardPriceDto);
}
