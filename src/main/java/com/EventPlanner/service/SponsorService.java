package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SponsorDto;

import java.util.List;

public interface SponsorService {
    SponsorDto save(SponsorDto sponsorDto);
    List<SponsorDto> getAll();
    PaginationResponse getAllPaginatedSponsor(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    SponsorDto findById(Long id);
    void deleteById(Long id);
    SponsorDto update(Long id, SponsorDto sponsorDto);
}
