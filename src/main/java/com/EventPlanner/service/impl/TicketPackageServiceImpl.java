package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TicketPackageDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.TicketPackage;
import com.EventPlanner.repository.EventRepository;
import com.EventPlanner.repository.TicketPackageRepository;
import com.EventPlanner.service.TicketPackageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketPackageServiceImpl implements TicketPackageService {
    private final TicketPackageRepository ticketPackageRepository;
    private final EventRepository eventRepository;

    public TicketPackageServiceImpl(TicketPackageRepository ticketPackageRepository, EventRepository eventRepository) {
        this.ticketPackageRepository = ticketPackageRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public TicketPackageDto save(TicketPackageDto ticketPackageDto) {
        TicketPackage ticketPackage = toEntity(ticketPackageDto);
        ticketPackage.setStatus(true);

        Event event = eventRepository.findById(ticketPackage.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", ticketPackage.getEvent().getId())));

        ticketPackage.setEvent(event);
        TicketPackage createdTicketPackage = ticketPackageRepository.save(ticketPackage);
        return toDto(createdTicketPackage);
    }

    @Override
    public List<TicketPackageDto> getAll() {
        List<TicketPackage> ticketPackageList = ticketPackageRepository.findAllInDesOrderByIdAndStatus();
        List<TicketPackageDto> ticketPackageDtoList = new ArrayList<>();

        for (TicketPackage ticketPackage : ticketPackageList) {
            TicketPackageDto ticketPackageDto = toDto(ticketPackage);
            ticketPackageDtoList.add(ticketPackageDto);
        }
        return ticketPackageDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedTicketPackage(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<TicketPackage> pageTicketPackage = ticketPackageRepository.findAllInDesOrderByIdAndStatus(page);
        List<TicketPackage> ticketPackageList = pageTicketPackage.getContent();

        List<TicketPackageDto> ticketPackageDtoList = new ArrayList<>();
        for (TicketPackage ticketPackage : ticketPackageList) {
            TicketPackageDto ticketPackageTypeDto = toDto(ticketPackage);
            ticketPackageDtoList.add(ticketPackageTypeDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(ticketPackageDtoList);
        paginationResponse.setPageNumber(pageTicketPackage.getNumber());
        paginationResponse.setPageSize(pageTicketPackage.getSize());
        paginationResponse.setTotalElements(pageTicketPackage.getNumberOfElements());
        paginationResponse.setTotalPages(pageTicketPackage.getTotalPages());
        paginationResponse.setLastPage(pageTicketPackage.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByComboType(String comboType, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<TicketPackage> pageTicketPackage = ticketPackageRepository.findTicketPackageByComboType(comboType,page);
        List<TicketPackage> ticketPackageList = pageTicketPackage.getContent();

        List<TicketPackageDto> ticketPackageDtoList = new ArrayList<>();
        for (TicketPackage ticketPackage : ticketPackageList) {
            TicketPackageDto ticketPackageTypeDto = toDto(ticketPackage);
            ticketPackageDtoList.add(ticketPackageTypeDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(ticketPackageDtoList);
        paginationResponse.setPageNumber(pageTicketPackage.getNumber());
        paginationResponse.setPageSize(pageTicketPackage.getSize());
        paginationResponse.setTotalElements(pageTicketPackage.getNumberOfElements());
        paginationResponse.setTotalPages(pageTicketPackage.getTotalPages());
        paginationResponse.setLastPage(pageTicketPackage.isLast());

        return paginationResponse;
    }

    @Override
    public TicketPackageDto findById(Long id) {
        TicketPackage ticketPackage = ticketPackageRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Package not found for id => %d", id)));
        return toDto(ticketPackage);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        TicketPackage ticketPackage = ticketPackageRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("TicketPackage not found for id => %d", id)));
        ticketPackageRepository.setStatusInactive(ticketPackage.getId());
    }

    @Override
    @Transactional
    public TicketPackageDto update(Long id, TicketPackageDto ticketPackageDto) {
        TicketPackage existingTicketPackage = ticketPackageRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Package not found for id => %d", id)));

        existingTicketPackage.setComboType(ticketPackageDto.getComboType());
        existingTicketPackage.setTotalPrice(ticketPackageDto.getTotalPrice());
        existingTicketPackage.setPrice(ticketPackageDto.getPrice());
        existingTicketPackage.setDiscountPrice(ticketPackageDto.getDiscountPrice());

        existingTicketPackage.setEvent(eventRepository.findById(ticketPackageDto.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", ticketPackageDto.getEvent().getId()))));

        TicketPackage updatedTicketPackage = ticketPackageRepository.save(existingTicketPackage);
        return toDto(updatedTicketPackage);
    }

    public TicketPackageDto toDto(TicketPackage ticketPackage) {
        return TicketPackageDto.builder()
                .id(ticketPackage.getId())
                .comboType(ticketPackage.getComboType())
                .totalPrice(ticketPackage.getTotalPrice())
                .price(ticketPackage.getPrice())
                .discountPrice(ticketPackage.getDiscountPrice())
                .status(ticketPackage.getStatus())
                .event(ticketPackage.getEvent())
                .build();
    }

    public TicketPackage toEntity(TicketPackageDto ticketPackageDto) {
        return TicketPackage.builder()
                .id(ticketPackageDto.getId())
                .comboType(ticketPackageDto.getComboType())
                .totalPrice(ticketPackageDto.getTotalPrice())
                .price(ticketPackageDto.getPrice())
                .discountPrice(ticketPackageDto.getDiscountPrice())
                .status(ticketPackageDto.getStatus())
                .event(ticketPackageDto.getEvent())
                .build();
    }
}
