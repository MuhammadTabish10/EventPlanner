package com.EventPlanner.service;

import com.EventPlanner.dto.EventTypeDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface EventTypeService {
    EventTypeDto save(EventTypeDto eventTypeDto);
    List<EventTypeDto> getAll();
    PaginationResponse getAllPaginatedEventType(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    EventTypeDto findById(Long id);
    EventTypeDto findByName(String name);
    void deleteById(Long id);
    EventTypeDto update(Long id, EventTypeDto eventTypeDto);
}
