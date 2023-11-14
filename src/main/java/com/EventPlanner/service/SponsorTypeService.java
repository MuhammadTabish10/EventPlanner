package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SponsorTypeDto;

import java.util.List;

public interface SponsorTypeService {
    SponsorTypeDto save(SponsorTypeDto sponsorTypeDto);
    List<SponsorTypeDto> getAll();
    PaginationResponse getAllPaginatedSponsorType(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByType(String type, Integer pageNumber, Integer pageSize);
    SponsorTypeDto findById(Long id);
    SponsorTypeDto findByType(String type);
    void deleteById(Long id);
    SponsorTypeDto update(Long id, SponsorTypeDto sponsorTypeDto);
}
