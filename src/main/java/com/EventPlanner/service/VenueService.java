package com.EventPlanner.service;

import com.EventPlanner.dto.VenueDto;

import java.util.List;

public interface VenueService {
    VenueDto save(VenueDto venueDto);
    List<VenueDto> getAll();
    VenueDto findById(Long id);
    VenueDto findByName(String name);
    List<VenueDto> searchByName(String name);
    void deleteById(Long id);
    VenueDto update(Long id, VenueDto venueDto);
}
