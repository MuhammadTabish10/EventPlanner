package com.EventPlanner.service;

import com.EventPlanner.dto.EventTypeDto;

import java.util.List;

public interface EventTypeService {
    EventTypeDto save(EventTypeDto eventTypeDto);
    List<EventTypeDto> getAll();
    EventTypeDto findById(Long id);
    EventTypeDto findByName(String name);
    List<EventTypeDto> searchByName(String name);
    void deleteById(Long id);
    EventTypeDto update(Long id, EventTypeDto eventTypeDto);
}
