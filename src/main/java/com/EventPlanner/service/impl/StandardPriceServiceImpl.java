package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.StandardPriceDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.StandardPrice;
import com.EventPlanner.model.Ticket;
import com.EventPlanner.repository.EventRepository;
import com.EventPlanner.repository.StandardPriceRepository;
import com.EventPlanner.repository.TicketRepository;
import com.EventPlanner.service.StandardPriceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StandardPriceServiceImpl implements StandardPriceService {
    private final StandardPriceRepository standardPriceRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    public StandardPriceServiceImpl(StandardPriceRepository standardPriceRepository, EventRepository eventRepository, TicketRepository ticketRepository) {
        this.standardPriceRepository = standardPriceRepository;
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public StandardPriceDto save(StandardPriceDto standardPriceDto) {
        StandardPrice standardPrice = toEntity(standardPriceDto);
        standardPrice.setStatus(true);

        Event event = eventRepository.findById(standardPrice.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", standardPrice.getEvent().getId())));

        Ticket ticket = ticketRepository.findById(standardPrice.getTicket().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket not found for id => %d", standardPrice.getTicket().getId())));

        standardPrice.setEvent(event);
        standardPrice.setTicket(ticket);
        StandardPrice createdStandardPrice = standardPriceRepository.save(standardPrice);
        return toDto(createdStandardPrice);
    }

    @Override
    public List<StandardPriceDto> getAll() {
        List<StandardPrice> standardPriceList = standardPriceRepository.findAllInDesOrderByIdAndStatus();
        List<StandardPriceDto> standardPriceDtoList = new ArrayList<>();

        for (StandardPrice standardPrice : standardPriceList) {
            StandardPriceDto standardPriceDto = toDto(standardPrice);
            standardPriceDtoList.add(standardPriceDto);
        }
        return standardPriceDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedStandardPrice(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<StandardPrice> pageStandardPrice = standardPriceRepository.findAllInDesOrderByIdAndStatus(page);
        List<StandardPrice> standardPriceList = pageStandardPrice.getContent();

        List<StandardPriceDto> standardPriceDtoList = new ArrayList<>();
        for (StandardPrice standardPrice : standardPriceList) {
            StandardPriceDto standardPriceDto = toDto(standardPrice);
            standardPriceDtoList.add(standardPriceDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(standardPriceDtoList);
        paginationResponse.setPageNumber(pageStandardPrice.getNumber());
        paginationResponse.setPageSize(pageStandardPrice.getSize());
        paginationResponse.setTotalElements(pageStandardPrice.getNumberOfElements());
        paginationResponse.setTotalPages(pageStandardPrice.getTotalPages());
        paginationResponse.setLastPage(pageStandardPrice.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByTicketName(String ticketName, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<StandardPrice> pageStandardPrice = standardPriceRepository.findStandardPriceByTicketName(ticketName,page);
        List<StandardPrice> standardPriceList = pageStandardPrice.getContent();

        List<StandardPriceDto> standardPriceDtoList = new ArrayList<>();
        for (StandardPrice standardPrice : standardPriceList) {
            StandardPriceDto standardPriceDto = toDto(standardPrice);
            standardPriceDtoList.add(standardPriceDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(standardPriceDtoList);
        paginationResponse.setPageNumber(pageStandardPrice.getNumber());
        paginationResponse.setPageSize(pageStandardPrice.getSize());
        paginationResponse.setTotalElements(pageStandardPrice.getNumberOfElements());
        paginationResponse.setTotalPages(pageStandardPrice.getTotalPages());
        paginationResponse.setLastPage(pageStandardPrice.isLast());

        return paginationResponse;
    }

    @Override
    public StandardPriceDto findById(Long id) {
        StandardPrice standardPrice = standardPriceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Standard Price not found for id => %d", id)));
        return toDto(standardPrice);
    }

    @Override
    public StandardPriceDto findByTicketName(String ticketName) {
        StandardPrice standardPrice = standardPriceRepository.findByTicketName(ticketName)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Standard Price not found for ticket name => %s", ticketName)));
        return toDto(standardPrice);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        StandardPrice standardPrice = standardPriceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("StandardPrice not found for id => %d", id)));
        standardPriceRepository.setStatusInactive(standardPrice.getId());
    }

    @Override
    @Transactional
    public StandardPriceDto update(Long id, StandardPriceDto standardPriceDto) {
        StandardPrice existingStandardPrice = standardPriceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("StandardPrice not found for id => %d", id)));

        existingStandardPrice.setTicketName(standardPriceDto.getTicketName());
        existingStandardPrice.setPrice(standardPriceDto.getPrice());
        existingStandardPrice.setStartDateAndTime(standardPriceDto.getStartDateAndTime());
        existingStandardPrice.setEndDateAndTime(standardPriceDto.getEndDateAndTime());

        existingStandardPrice.setEvent(eventRepository.findById(standardPriceDto.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", standardPriceDto.getEvent().getId()))));

        existingStandardPrice.setTicket(ticketRepository.findById(standardPriceDto.getTicket().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket not found for id => %d", standardPriceDto.getTicket().getId()))));

        StandardPrice updatedStandardPrice = standardPriceRepository.save(existingStandardPrice);
        return toDto(updatedStandardPrice);
    }

    public StandardPriceDto toDto(StandardPrice standardPrice) {
        return StandardPriceDto.builder()
                .id(standardPrice.getId())
                .ticketName(standardPrice.getTicketName())
                .price(standardPrice.getPrice())
                .status(standardPrice.getStatus())
                .startDateAndTime(standardPrice.getStartDateAndTime())
                .endDateAndTime(standardPrice.getEndDateAndTime())
                .event(standardPrice.getEvent())
                .ticket(standardPrice.getTicket())
                .build();
    }

    public StandardPrice toEntity(StandardPriceDto standardPriceDto) {
        return StandardPrice.builder()
                .id(standardPriceDto.getId())
                .ticketName(standardPriceDto.getTicketName())
                .price(standardPriceDto.getPrice())
                .status(standardPriceDto.getStatus())
                .startDateAndTime(standardPriceDto.getStartDateAndTime())
                .endDateAndTime(standardPriceDto.getEndDateAndTime())
                .event(standardPriceDto.getEvent())
                .ticket(standardPriceDto.getTicket())
                .build();
    }
}
