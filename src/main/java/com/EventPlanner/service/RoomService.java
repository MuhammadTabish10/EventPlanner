package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto save(RoomDto roomDto);
    List<RoomDto> getAll();
    PaginationResponse getAllPaginatedRoom(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    RoomDto findById(Long id);
    RoomDto findByName(String name);
    void deleteById(Long id);
    RoomDto update(Long id, RoomDto roomDto);
}
