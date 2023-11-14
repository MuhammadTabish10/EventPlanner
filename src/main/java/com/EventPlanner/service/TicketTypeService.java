package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TicketTypeDto;

import java.util.List;

public interface TicketTypeService {
    TicketTypeDto save(TicketTypeDto ticketTypeDto);
    List<TicketTypeDto> getAll();
    PaginationResponse getAllPaginatedTicketType(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByType(String type, Integer pageNumber, Integer pageSize);
    TicketTypeDto findById(Long id);
    TicketTypeDto findByType(String type);
    void deleteById(Long id);
    TicketTypeDto update(Long id, TicketTypeDto ticketTypeDto);
}
