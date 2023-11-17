package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TicketDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.*;
import com.EventPlanner.repository.*;
import com.EventPlanner.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketPackageRepository ticketPackageRepository;
    private final AccountRepository accountRepository;
    private final EventRepository eventRepository;
    private final TicketTypeRepository ticketTypeRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketPackageRepository ticketPackageRepository, AccountRepository accountRepository, EventRepository eventRepository, TicketTypeRepository ticketTypeRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketPackageRepository = ticketPackageRepository;
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    @Transactional
    public TicketDto save(TicketDto ticketDto) {
        Ticket ticket = toEntity(ticketDto);
        ticket.setStatus(true);

        Account account = accountRepository.findById(ticket.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", ticket.getAccount().getId())));

        Event event = eventRepository.findById(ticket.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", ticket.getEvent().getId())));

        TicketPackage ticketPackage = ticketPackageRepository.findById(ticket.getTicketPackage().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Package not found for id => %d", ticket.getTicketPackage().getId())));

        TicketType ticketType = ticketTypeRepository.findById(ticket.getTicketType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Type not found for id => %d", ticket.getTicketType().getId())));

        ticket.setAccount(account);
        ticket.setEvent(event);
        ticket.setTicketPackage(ticketPackage);
        ticket.setTicketType(ticketType);
        Ticket createdTicket = ticketRepository.save(ticket);
        return toDto(createdTicket);
    }

    @Override
    public List<TicketDto> getAll() {
        List<Ticket> ticketList = ticketRepository.findAllInDesOrderByIdAndStatus();
        List<TicketDto> ticketDtoList = new ArrayList<>();

        for (Ticket ticket : ticketList) {
            TicketDto ticketDto = toDto(ticket);
            ticketDtoList.add(ticketDto);
        }
        return ticketDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedTicket(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Ticket> pageTicket = ticketRepository.findAllInDesOrderByIdAndStatus(page);
        List<Ticket> ticketList = pageTicket.getContent();

        List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            TicketDto ticketDto = toDto(ticket);
            ticketDtoList.add(ticketDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(ticketDtoList);
        paginationResponse.setPageNumber(pageTicket.getNumber());
        paginationResponse.setPageSize(pageTicket.getSize());
        paginationResponse.setTotalElements(pageTicket.getNumberOfElements());
        paginationResponse.setTotalPages(pageTicket.getTotalPages());
        paginationResponse.setLastPage(pageTicket.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Ticket> pageTicket = ticketRepository.findTicketByName(name,page);
        List<Ticket> ticketList = pageTicket.getContent();

        List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            TicketDto ticketDto = toDto(ticket);
            ticketDtoList.add(ticketDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(ticketDtoList);
        paginationResponse.setPageNumber(pageTicket.getNumber());
        paginationResponse.setPageSize(pageTicket.getSize());
        paginationResponse.setTotalElements(pageTicket.getNumberOfElements());
        paginationResponse.setTotalPages(pageTicket.getTotalPages());
        paginationResponse.setLastPage(pageTicket.isLast());

        return paginationResponse;
    }

    @Override
    public TicketDto findById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket not found for id => %d", id)));
        return toDto(ticket);
    }

    @Override
    public TicketDto findByName(String name) {
        Ticket ticket = ticketRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket not found for type => %s", name)));
        return toDto(ticket);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket not found for id => %d", id)));
        ticketRepository.setStatusInactive(ticket.getId());
    }

    @Override
    @Transactional
    public TicketDto update(Long id, TicketDto ticketDto) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket not found for id => %d", id)));

        existingTicket.setName(ticketDto.getName());
        existingTicket.setSku(ticketDto.getSku());
        existingTicket.setType(ticketDto.getType());
        existingTicket.setDescription(ticketDto.getDescription());
        existingTicket.setIsTable(ticketDto.getIsTable());
        existingTicket.setMaxSeats(ticketDto.getMaxSeats());
        existingTicket.setRestrictions(ticketDto.getRestrictions());

        existingTicket.setAccount(accountRepository.findById(ticketDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", ticketDto.getAccount().getId()))));

        existingTicket.setEvent(eventRepository.findById(ticketDto.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", ticketDto.getEvent().getId()))));

        existingTicket.setTicketPackage(ticketPackageRepository.findById(ticketDto.getTicketPackage().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Package not found for id => %d", ticketDto.getTicketPackage().getId()))));

        existingTicket.setTicketType(ticketTypeRepository.findById(ticketDto.getTicketType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Type not found for id => %d", ticketDto.getTicketType().getId()))));

        Ticket updatedTicket = ticketRepository.save(existingTicket);
        return toDto(updatedTicket);
    }

    public TicketDto toDto(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .createdAt(ticket.getCreatedAt())
                .name(ticket.getName())
                .sku(ticket.getSku())
                .type(ticket.getType())
                .description(ticket.getDescription())
                .isTable(ticket.getIsTable())
                .maxSeats(ticket.getMaxSeats())
                .restrictions(ticket.getRestrictions())
                .status(ticket.getStatus())
                .ticketPackage(ticket.getTicketPackage())
                .account(ticket.getAccount())
                .event(ticket.getEvent())
                .ticketType(ticket.getTicketType())
                .build();
    }

    public Ticket toEntity(TicketDto ticketDto) {
        return Ticket.builder()
                .id(ticketDto.getId())
                .createdAt(ticketDto.getCreatedAt())
                .name(ticketDto.getName())
                .sku(ticketDto.getSku())
                .type(ticketDto.getType())
                .description(ticketDto.getDescription())
                .isTable(ticketDto.getIsTable())
                .maxSeats(ticketDto.getMaxSeats())
                .restrictions(ticketDto.getRestrictions())
                .status(ticketDto.getStatus())
                .ticketPackage(ticketDto.getTicketPackage())
                .account(ticketDto.getAccount())
                .event(ticketDto.getEvent())
                .ticketType(ticketDto.getTicketType())
                .build();
    }
}
