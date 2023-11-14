package com.EventPlanner.service.impl;

import com.EventPlanner.dto.IndustryDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Industry;
import com.EventPlanner.repository.IndustryRepository;
import com.EventPlanner.service.IndustryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndustryServiceImpl implements IndustryService {
    private final IndustryRepository industryRepository;

    public IndustryServiceImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Override
    @Transactional
    public IndustryDto save(IndustryDto industryDto) {
        Industry industry = toEntity(industryDto);
        industry.setStatus(true);
        Industry createdIndustry = industryRepository.save(industry);
        return toDto(createdIndustry);
    }

    @Override
    public List<IndustryDto> getAll() {
        List<Industry> industryList = industryRepository.findAllInDesOrderByIdAndStatus();
        List<IndustryDto> industryDtoList = new ArrayList<>();

        for (Industry industry : industryList) {
            IndustryDto industryDto = toDto(industry);
            industryDtoList.add(industryDto);
        }
        return industryDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedIndustry(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Industry> pageIndustry = industryRepository.findAllInDesOrderByIdAndStatus(page);
        List<Industry> industryList = pageIndustry.getContent();

        List<IndustryDto> industryDtoList = new ArrayList<>();
        for (Industry industry : industryList) {
            IndustryDto industryDto = toDto(industry);
            industryDtoList.add(industryDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(industryDtoList);
        paginationResponse.setPageNumber(pageIndustry.getNumber());
        paginationResponse.setPageSize(pageIndustry.getSize());
        paginationResponse.setTotalElements(pageIndustry.getNumberOfElements());
        paginationResponse.setTotalPages(pageIndustry.getTotalPages());
        paginationResponse.setLastPage(pageIndustry.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Industry> pageIndustry = industryRepository.findIndustryByName(name,page);
        List<Industry> industryList = pageIndustry.getContent();

        List<IndustryDto> industryDtoList = new ArrayList<>();
        for (Industry industry : industryList) {
            IndustryDto industryDto = toDto(industry);
            industryDtoList.add(industryDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(industryDtoList);
        paginationResponse.setPageNumber(pageIndustry.getNumber());
        paginationResponse.setPageSize(pageIndustry.getSize());
        paginationResponse.setTotalElements(pageIndustry.getNumberOfElements());
        paginationResponse.setTotalPages(pageIndustry.getTotalPages());
        paginationResponse.setLastPage(pageIndustry.isLast());

        return paginationResponse;
    }

    @Override
    public IndustryDto findById(Long id) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Industry not found for id => %d", id)));
        return toDto(industry);
    }

    @Override
    public IndustryDto findByName(String name) {
        Industry industry = industryRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Industry not found for name => %s", name)));
        return toDto(industry);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Industry not found for id => %d", id)));
        industryRepository.setStatusInactive(industry.getId());
    }

    @Override
    @Transactional
    public IndustryDto update(Long id, IndustryDto industryDto) {
        Industry existingIndustry = industryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Industry not found for id => %d", id)));

        existingIndustry.setName(industryDto.getName());

        Industry updatedIndustry = industryRepository.save(existingIndustry);
        return toDto(updatedIndustry);
    }

    public IndustryDto toDto(Industry industry) {
        return IndustryDto.builder()
                .id(industry.getId())
                .name(industry.getName())
                .status(industry.getStatus())
                .build();
    }

    public Industry toEntity(IndustryDto industryDto) {
        return Industry.builder()
                .id(industryDto.getId())
                .name(industryDto.getName())
                .status(industryDto.getStatus())
                .build();
    }
}
