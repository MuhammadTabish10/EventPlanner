package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TicketPackageDto;

import java.util.List;

public interface TicketPackageService {
    TicketPackageDto save(TicketPackageDto ticketPackageDto);
    List<TicketPackageDto> getAll();
    PaginationResponse getAllPaginatedTicketPackage(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByComboType(String comboType, Integer pageNumber, Integer pageSize);
    TicketPackageDto findById(Long id);
    void deleteById(Long id);
    TicketPackageDto update(Long id, TicketPackageDto ticketPackageDto);
}
