package com.EventPlanner.service;

import com.EventPlanner.dto.SponsorTypeDto;

import java.util.List;

public interface SponsorTypeService {
    SponsorTypeDto save(SponsorTypeDto sponsorTypeDto);
    List<SponsorTypeDto> getAll();
    SponsorTypeDto findById(Long id);
    SponsorTypeDto findByType(String type);
    List<SponsorTypeDto> searchByType(String type);
    void deleteById(Long id);
    SponsorTypeDto update(Long id, SponsorTypeDto sponsorTypeDto);
}
