package com.EventPlanner.service;

import com.EventPlanner.dto.TicketTypeDto;

import java.util.List;

public interface TicketTypeService {
    TicketTypeDto save(TicketTypeDto ticketTypeDto);
    List<TicketTypeDto> getAll();
    TicketTypeDto findById(Long id);
    TicketTypeDto findByType(String type);
    List<TicketTypeDto> searchByType(String name);
    void deleteById(Long id);
    TicketTypeDto update(Long id, TicketTypeDto ticketTypeDto);
}
