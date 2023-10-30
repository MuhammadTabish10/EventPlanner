package com.EventPlanner.service;

import com.EventPlanner.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto save(RoomDto roomDto);
    List<RoomDto> getAll();
    RoomDto findById(Long id);
    RoomDto findByName(String name);
    List<RoomDto> searchByName(String name);
    void deleteById(Long id);
    RoomDto update(Long id, RoomDto roomDto);
}
