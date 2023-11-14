package com.EventPlanner.service;

import com.EventPlanner.dto.EventDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface EventService {
    EventDto save(EventDto eventDto);
    List<EventDto> getAll();
    PaginationResponse getAllPaginatedEvent(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    EventDto findById(Long id);
    EventDto findByName(String name);
    void deleteById(Long id);
    EventDto update(Long id, EventDto eventDto);
}
