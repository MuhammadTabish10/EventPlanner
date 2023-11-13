package com.EventPlanner.service;

import com.EventPlanner.dto.EventDto;

import java.util.List;

public interface EventService {
    EventDto save(EventDto eventDto);
    List<EventDto> getAll();
    EventDto findById(Long id);
    EventDto findByName(String name);
    List<EventDto> searchByName(String name);
    void deleteById(Long id);
    EventDto update(Long id, EventDto eventDto);
}
