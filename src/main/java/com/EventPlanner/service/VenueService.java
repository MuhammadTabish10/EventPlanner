package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.VenueDto;

import java.util.List;

public interface VenueService {
    VenueDto save(VenueDto venueDto);
    List<VenueDto> getAll();
    PaginationResponse getAllPaginatedVenue(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    VenueDto findById(Long id);
    VenueDto findByName(String name);
    void deleteById(Long id);
    VenueDto update(Long id, VenueDto venueDto);
}
