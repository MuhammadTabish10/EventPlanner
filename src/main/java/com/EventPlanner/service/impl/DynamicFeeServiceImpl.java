package com.EventPlanner.service.impl;

import com.EventPlanner.dto.DynamicFeeDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.DynamicFee;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.Ticket;
import com.EventPlanner.repository.DynamicFeeRepository;
import com.EventPlanner.repository.EventRepository;
import com.EventPlanner.repository.TicketRepository;
import com.EventPlanner.service.DynamicFeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicFeeServiceImpl implements DynamicFeeService {
    private final DynamicFeeRepository dynamicFeeRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    public DynamicFeeServiceImpl(DynamicFeeRepository dynamicFeeRepository, EventRepository eventRepository, TicketRepository ticketRepository) {
        this.dynamicFeeRepository = dynamicFeeRepository;
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public DynamicFeeDto save(DynamicFeeDto dynamicFeeDto) {
        DynamicFee dynamicFee = toEntity(dynamicFeeDto);
        dynamicFee.setStatus(true);

        Event event = eventRepository.findById(dynamicFee.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", dynamicFee.getEvent().getId())));

        Ticket ticket = ticketRepository.findById(dynamicFee.getTicket().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket not found for id => %d", dynamicFee.getTicket().getId())));

        dynamicFee.setEvent(event);
        dynamicFee.setTicket(ticket);
        DynamicFee createdDynamicFee = dynamicFeeRepository.save(dynamicFee);
        return toDto(createdDynamicFee);
    }

    @Override
    public List<DynamicFeeDto> getAll() {
        List<DynamicFee> dynamicFeeList = dynamicFeeRepository.findAllInDesOrderByIdAndStatus();
        List<DynamicFeeDto> dynamicFeeDtoList = new ArrayList<>();

        for (DynamicFee dynamicFee : dynamicFeeList) {
            DynamicFeeDto dynamicFeeDto = toDto(dynamicFee);
            dynamicFeeDtoList.add(dynamicFeeDto);
        }
        return dynamicFeeDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedDynamicFee(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DynamicFee> pageDynamicFee = dynamicFeeRepository.findAllInDesOrderByIdAndStatus(page);
        List<DynamicFee> dynamicFeeList = pageDynamicFee.getContent();

        List<DynamicFeeDto> dynamicFeeDtoList = new ArrayList<>();
        for (DynamicFee dynamicFee : dynamicFeeList) {
            DynamicFeeDto dynamicFeeDto = toDto(dynamicFee);
            dynamicFeeDtoList.add(dynamicFeeDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(dynamicFeeDtoList);
        paginationResponse.setPageNumber(pageDynamicFee.getNumber());
        paginationResponse.setPageSize(pageDynamicFee.getSize());
        paginationResponse.setTotalElements(pageDynamicFee.getNumberOfElements());
        paginationResponse.setTotalPages(pageDynamicFee.getTotalPages());
        paginationResponse.setLastPage(pageDynamicFee.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByTicketName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DynamicFee> pageDynamicFee = dynamicFeeRepository.findDynamicFeeByTicketName(name,page);
        List<DynamicFee> dynamicFeeList = pageDynamicFee.getContent();

        List<DynamicFeeDto> dynamicFeeDtoList = new ArrayList<>();
        for (DynamicFee dynamicFee : dynamicFeeList) {
            DynamicFeeDto dynamicFeeDto = toDto(dynamicFee);
            dynamicFeeDtoList.add(dynamicFeeDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(dynamicFeeDtoList);
        paginationResponse.setPageNumber(pageDynamicFee.getNumber());
        paginationResponse.setPageSize(pageDynamicFee.getSize());
        paginationResponse.setTotalElements(pageDynamicFee.getNumberOfElements());
        paginationResponse.setTotalPages(pageDynamicFee.getTotalPages());
        paginationResponse.setLastPage(pageDynamicFee.isLast());

        return paginationResponse;
    }

    @Override
    public DynamicFeeDto findById(Long id) {
        DynamicFee dynamicFee = dynamicFeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Standard Price not found for id => %d", id)));
        return toDto(dynamicFee);
    }

    @Override
    public DynamicFeeDto findByTicketName(String name) {
        DynamicFee dynamicFee = dynamicFeeRepository.findByTicketName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Dynamic Fee not found for ticket name => %s", name)));
        return toDto(dynamicFee);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        DynamicFee dynamicFee = dynamicFeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Dynamic Fee not found for id => %d", id)));
        dynamicFeeRepository.setStatusInactive(dynamicFee.getId());
    }

    @Override
    @Transactional
    public DynamicFeeDto update(Long id, DynamicFeeDto dynamicFeeDto) {
        DynamicFee existingDynamicFee = dynamicFeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("DynamicFee not found for id => %d", id)));

        existingDynamicFee.setTicketName(dynamicFeeDto.getTicketName());
        existingDynamicFee.setQty(dynamicFeeDto.getQty());
        existingDynamicFee.setStandardPrice(dynamicFeeDto.getStandardPrice());
        existingDynamicFee.setDiscountedPrice(dynamicFeeDto.getDiscountedPrice());
        existingDynamicFee.setDiscountType(dynamicFeeDto.getDiscountType());
        existingDynamicFee.setPercent(dynamicFeeDto.getPercent());
        existingDynamicFee.setPrice(dynamicFeeDto.getPrice());
        existingDynamicFee.setMaxAttendees(dynamicFeeDto.getMaxAttendees());
        existingDynamicFee.setStartDateAndTime(dynamicFeeDto.getStartDateAndTime());
        existingDynamicFee.setEndDateAndTime(dynamicFeeDto.getEndDateAndTime());


        existingDynamicFee.setEvent(eventRepository.findById(dynamicFeeDto.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", dynamicFeeDto.getEvent().getId()))));

        existingDynamicFee.setTicket(ticketRepository.findById(dynamicFeeDto.getTicket().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket not found for id => %d", dynamicFeeDto.getTicket().getId()))));

        DynamicFee updatedDynamicFee = dynamicFeeRepository.save(existingDynamicFee);
        return toDto(updatedDynamicFee);
    }

    public DynamicFeeDto toDto(DynamicFee dynamicFee) {
        return DynamicFeeDto.builder()
                .id(dynamicFee.getId())
                .ticketName(dynamicFee.getTicketName())
                .qty(dynamicFee.getQty())
                .standardPrice(dynamicFee.getStandardPrice())
                .discountType(dynamicFee.getDiscountType())
                .percent(dynamicFee.getPercent())
                .price(dynamicFee.getPrice())
                .status(dynamicFee.getStatus())
                .startDateAndTime(dynamicFee.getStartDateAndTime())
                .endDateAndTime(dynamicFee.getEndDateAndTime())
                .discountedPrice(dynamicFee.getDiscountedPrice())
                .maxAttendees(dynamicFee.getMaxAttendees())
                .event(dynamicFee.getEvent())
                .ticket(dynamicFee.getTicket())
                .build();
    }

    public DynamicFee toEntity(DynamicFeeDto dynamicFeeDto) {
        return DynamicFee.builder()
                .id(dynamicFeeDto.getId())
                .ticketName(dynamicFeeDto.getTicketName())
                .qty(dynamicFeeDto.getQty())
                .standardPrice(dynamicFeeDto.getStandardPrice())
                .discountType(dynamicFeeDto.getDiscountType())
                .percent(dynamicFeeDto.getPercent())
                .price(dynamicFeeDto.getPrice())
                .status(dynamicFeeDto.getStatus())
                .startDateAndTime(dynamicFeeDto.getStartDateAndTime())
                .endDateAndTime(dynamicFeeDto.getEndDateAndTime())
                .discountedPrice(dynamicFeeDto.getDiscountedPrice())
                .maxAttendees(dynamicFeeDto.getMaxAttendees())
                .event(dynamicFeeDto.getEvent())
                .ticket(dynamicFeeDto.getTicket())
                .build();
    }
}
