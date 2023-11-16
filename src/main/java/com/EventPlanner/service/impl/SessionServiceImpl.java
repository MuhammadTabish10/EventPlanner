package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SessionDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.Room;
import com.EventPlanner.model.Session;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.EventRepository;
import com.EventPlanner.repository.RoomRepository;
import com.EventPlanner.repository.SessionRepository;
import com.EventPlanner.service.SessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final AccountRepository accountRepository;
    private final RoomRepository roomRepository;
    private final EventRepository eventRepository;

    public SessionServiceImpl(SessionRepository sessionRepository, AccountRepository accountRepository, RoomRepository roomRepository, EventRepository eventRepository) {
        this.sessionRepository = sessionRepository;
        this.accountRepository = accountRepository;
        this.roomRepository = roomRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public SessionDto save(SessionDto sessionDto) {
        Session session = toEntity(sessionDto);
        session.setStatus(true);

        Account account = accountRepository.findById(session.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", session.getAccount().getId())));

        Room room = roomRepository.findById(session.getRoom().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Room not found for id => %d", session.getRoom().getId())));

        Event event = eventRepository.findById(session.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", session.getEvent().getId())));

        session.setAccount(account);
        session.setRoom(room);
        session.setEvent(event);
        Session createdSession = sessionRepository.save(session);
        return toDto(createdSession);
    }

    @Override
    public List<SessionDto> getAll() {
        List<Session> sessionList = sessionRepository.findAllInDesOrderByIdAndStatus();
        List<SessionDto> sessionDtoList = new ArrayList<>();

        for (Session session : sessionList) {
            SessionDto sessionDto = toDto(session);
            sessionDtoList.add(sessionDto);
        }
        return sessionDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedSession(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Session> pageSession = sessionRepository.findAllInDesOrderByIdAndStatus(page);
        List<Session> sessionList = pageSession.getContent();

        List<SessionDto> sessionDtoList = new ArrayList<>();
        for (Session session : sessionList) {
            SessionDto sessionDto = toDto(session);
            sessionDtoList.add(sessionDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(sessionDtoList);
        paginationResponse.setPageNumber(pageSession.getNumber());
        paginationResponse.setPageSize(pageSession.getSize());
        paginationResponse.setTotalElements(pageSession.getNumberOfElements());
        paginationResponse.setTotalPages(pageSession.getTotalPages());
        paginationResponse.setLastPage(pageSession.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Session> pageSession = sessionRepository.findSessionByName(name,page);
        List<Session> sessionList = pageSession.getContent();

        List<SessionDto> sessionDtoList = new ArrayList<>();
        for (Session session : sessionList) {
            SessionDto sessionDto = toDto(session);
            sessionDtoList.add(sessionDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(sessionDtoList);
        paginationResponse.setPageNumber(pageSession.getNumber());
        paginationResponse.setPageSize(pageSession.getSize());
        paginationResponse.setTotalElements(pageSession.getNumberOfElements());
        paginationResponse.setTotalPages(pageSession.getTotalPages());
        paginationResponse.setLastPage(pageSession.isLast());

        return paginationResponse;
    }

    @Override
    public SessionDto findById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Session not found for id => %d", id)));
        return toDto(session);
    }

    @Override
    public SessionDto findByName(String name) {
        Session session = sessionRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Session not found for first Name => %s", name)));
        return toDto(session);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Session not found for id => %d", id)));
        sessionRepository.setStatusInactive(session.getId());
    }

    @Override
    @Transactional
    public SessionDto update(Long id, SessionDto sessionDto) {
        Session existingSession = sessionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Session not found for id => %d", id)));

        existingSession.setName(sessionDto.getName());
        existingSession.setDescription(sessionDto.getDescription());
        existingSession.setCapacity(sessionDto.getCapacity());
        existingSession.setStartDateAndTime(sessionDto.getStartDateAndTime());
        existingSession.setEndDateAndTime(sessionDto.getEndDateAndTime());

        existingSession.setAccount(accountRepository.findById(sessionDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", sessionDto.getAccount().getId()))));

        existingSession.setRoom(roomRepository.findById(sessionDto.getRoom().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Room not found for id => %d", sessionDto.getRoom().getId()))));

        existingSession.setEvent(eventRepository.findById(sessionDto.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", sessionDto.getEvent().getId()))));

        Session updatedSession = sessionRepository.save(existingSession);
        return toDto(updatedSession);
    }

    public SessionDto toDto(Session session) {
        return SessionDto.builder()
                .id(session.getId())
                .name(session.getName())
                .description(session.getDescription())
                .capacity(session.getCapacity())
                .startDateAndTime(session.getStartDateAndTime())
                .endDateAndTime(session.getEndDateAndTime())
                .status(session.getStatus())
                .account(session.getAccount())
                .room(session.getRoom())
                .event(session.getEvent())
                .build();
    }

    public Session toEntity(SessionDto sessionDto) {
        return Session.builder()
                .id(sessionDto.getId())
                .name(sessionDto.getName())
                .description(sessionDto.getDescription())
                .capacity(sessionDto.getCapacity())
                .startDateAndTime(sessionDto.getStartDateAndTime())
                .endDateAndTime(sessionDto.getEndDateAndTime())
                .status(sessionDto.getStatus())
                .account(sessionDto.getAccount())
                .room(sessionDto.getRoom())
                .event(sessionDto.getEvent())
                .build();
    }
}
