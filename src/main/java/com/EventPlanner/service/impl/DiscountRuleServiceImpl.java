package com.EventPlanner.service.impl;

import com.EventPlanner.dto.DiscountRuleDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.DiscountRule;
import com.EventPlanner.model.Event;
import com.EventPlanner.repository.DiscountRuleRepository;
import com.EventPlanner.repository.EventRepository;
import com.EventPlanner.service.DiscountRuleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountRuleServiceImpl implements DiscountRuleService {
    private final DiscountRuleRepository discountRuleRepository;
    private final EventRepository eventRepository;

    public DiscountRuleServiceImpl(DiscountRuleRepository discountRuleRepository, EventRepository eventRepository) {
        this.discountRuleRepository = discountRuleRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public DiscountRuleDto save(DiscountRuleDto discountRuleDto) {
        DiscountRule discountRule = toEntity(discountRuleDto);
        discountRule.setStatus(true);

        Event event = eventRepository.findById(discountRule.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", discountRule.getEvent().getId())));

        discountRule.setEvent(event);
        DiscountRule createdDiscountRule = discountRuleRepository.save(discountRule);
        return toDto(createdDiscountRule);
    }

    @Override
    public List<DiscountRuleDto> getAll() {
        List<DiscountRule> discountRuleList = discountRuleRepository.findAllInDesOrderByIdAndStatus();
        List<DiscountRuleDto> discountRuleDtoList = new ArrayList<>();

        for (DiscountRule discountRule : discountRuleList) {
            DiscountRuleDto discountRuleDto = toDto(discountRule);
            discountRuleDtoList.add(discountRuleDto);
        }
        return discountRuleDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedDiscountRule(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DiscountRule> pageDiscountRule = discountRuleRepository.findAllInDesOrderByIdAndStatus(page);
        List<DiscountRule> discountRuleList = pageDiscountRule.getContent();

        List<DiscountRuleDto> discountRuleDtoList = new ArrayList<>();
        for (DiscountRule discountRule : discountRuleList) {
            DiscountRuleDto discountRuleDto = toDto(discountRule);
            discountRuleDtoList.add(discountRuleDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(discountRuleDtoList);
        paginationResponse.setPageNumber(pageDiscountRule.getNumber());
        paginationResponse.setPageSize(pageDiscountRule.getSize());
        paginationResponse.setTotalElements(pageDiscountRule.getNumberOfElements());
        paginationResponse.setTotalPages(pageDiscountRule.getTotalPages());
        paginationResponse.setLastPage(pageDiscountRule.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByDiscountCode(String discountCode, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DiscountRule> pageDiscountRule = discountRuleRepository.findDiscountRuleByDiscountCode(discountCode,page);
        List<DiscountRule> discountRuleList = pageDiscountRule.getContent();

        List<DiscountRuleDto> discountRuleDtoList = new ArrayList<>();
        for (DiscountRule discountRule : discountRuleList) {
            DiscountRuleDto discountRuleDto = toDto(discountRule);
            discountRuleDtoList.add(discountRuleDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(discountRuleDtoList);
        paginationResponse.setPageNumber(pageDiscountRule.getNumber());
        paginationResponse.setPageSize(pageDiscountRule.getSize());
        paginationResponse.setTotalElements(pageDiscountRule.getNumberOfElements());
        paginationResponse.setTotalPages(pageDiscountRule.getTotalPages());
        paginationResponse.setLastPage(pageDiscountRule.isLast());

        return paginationResponse;
    }

    @Override
    public DiscountRuleDto findById(Long id) {
        DiscountRule discountRule = discountRuleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Discount Rule not found for id => %d", id)));
        return toDto(discountRule);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        DiscountRule discountRule = discountRuleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Discount Rule not found for id => %d", id)));
        discountRuleRepository.setStatusInactive(discountRule.getId());
    }

    @Override
    @Transactional
    public DiscountRuleDto update(Long id, DiscountRuleDto discountRuleDto) {
        DiscountRule existingDiscountRule = discountRuleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Discount Rule not found for id => %d", id)));

        existingDiscountRule.setDiscountCode(discountRuleDto.getDiscountCode());
        existingDiscountRule.setDiscountType(discountRuleDto.getDiscountType());
        existingDiscountRule.setMaxAttendees(discountRuleDto.getMaxAttendees());
        existingDiscountRule.setStartDateAndTime(discountRuleDto.getStartDateAndTime());
        existingDiscountRule.setEndDateAndTime(discountRuleDto.getEndDateAndTime());

        existingDiscountRule.setEvent(eventRepository.findById(discountRuleDto.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", discountRuleDto.getEvent().getId()))));

        DiscountRule updatedDiscountRule = discountRuleRepository.save(existingDiscountRule);
        return toDto(updatedDiscountRule);
    }

    public DiscountRuleDto toDto(DiscountRule discountRule) {
        return DiscountRuleDto.builder()
                .id(discountRule.getId())
                .discountCode(discountRule.getDiscountCode())
                .discountType(discountRule.getDiscountType())
                .maxAttendees(discountRule.getMaxAttendees())
                .startDateAndTime(discountRule.getStartDateAndTime())
                .endDateAndTime(discountRule.getEndDateAndTime())
                .status(discountRule.getStatus())
                .event(discountRule.getEvent())
                .build();
    }

    public DiscountRule toEntity(DiscountRuleDto discountRuleDto) {
        return DiscountRule.builder()
                .id(discountRuleDto.getId())
                .discountCode(discountRuleDto.getDiscountCode())
                .discountType(discountRuleDto.getDiscountType())
                .maxAttendees(discountRuleDto.getMaxAttendees())
                .startDateAndTime(discountRuleDto.getStartDateAndTime())
                .endDateAndTime(discountRuleDto.getEndDateAndTime())
                .status(discountRuleDto.getStatus())
                .event(discountRuleDto.getEvent())
                .build();
    }
}
