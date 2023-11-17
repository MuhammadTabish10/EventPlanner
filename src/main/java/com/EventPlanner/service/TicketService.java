package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TicketDto;

import java.util.List;

public interface TicketService {
    TicketDto save(TicketDto ticketDto);
    List<TicketDto> getAll();
    PaginationResponse getAllPaginatedTicket(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    TicketDto findById(Long id);
    TicketDto findByName(String name);
    void deleteById(Long id);
    TicketDto update(Long id, TicketDto ticketDto);
}
