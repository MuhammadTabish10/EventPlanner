package com.EventPlanner.service.impl;

import com.EventPlanner.dto.IndustryDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Industry;
import com.EventPlanner.repository.IndustryRepository;
import com.EventPlanner.service.IndustryService;
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
    public List<IndustryDto> searchByName(String name) {
        List<Industry> industryList = industryRepository.findIndustryByName(name);
        List<IndustryDto> industryDtoList = new ArrayList<>();

        for (Industry industry : industryList) {
            IndustryDto industryDto = toDto(industry);
            industryDtoList.add(industryDto);
        }
        return industryDtoList;
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
