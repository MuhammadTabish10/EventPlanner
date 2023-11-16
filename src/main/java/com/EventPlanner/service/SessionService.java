package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SessionDto;

import java.util.List;

public interface SessionService {
    SessionDto save(SessionDto sessionDto);
    List<SessionDto> getAll();
    PaginationResponse getAllPaginatedSession(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    SessionDto findById(Long id);
    SessionDto findByName(String name);
    void deleteById(Long id);
    SessionDto update(Long id, SessionDto sessionDto);
}
