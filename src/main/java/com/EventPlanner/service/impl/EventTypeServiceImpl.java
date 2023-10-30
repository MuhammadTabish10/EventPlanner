package com.EventPlanner.service.impl;

import com.EventPlanner.dto.EventTypeDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.EventType;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.EventTypeRepository;
import com.EventPlanner.service.EventTypeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventTypeServiceImpl implements EventTypeService {

    private final EventTypeRepository eventTypeRepository;
    private final AccountRepository accountRepository;

    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository, AccountRepository accountRepository) {
        this.eventTypeRepository = eventTypeRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public EventTypeDto save(EventTypeDto eventTypeDto) {
        EventType eventType = toEntity(eventTypeDto);
        eventType.setStatus(true);

        Account account = accountRepository.findById(eventType.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", eventType.getAccount().getId())));

        eventType.setAccount(account);
        EventType createdEventType = eventTypeRepository.save(eventType);
        return toDto(createdEventType);
    }

    @Override
    public List<EventTypeDto> getAll() {
        List<EventType> eventTypeList = eventTypeRepository.findAllInDesOrderByIdAndStatus();
        List<EventTypeDto> eventTypeDtoList = new ArrayList<>();

        for (EventType eventType : eventTypeList) {
            EventTypeDto eventTypeDto = toDto(eventType);
            eventTypeDtoList.add(eventTypeDto);
        }
        return eventTypeDtoList;
    }

    @Override
    public EventTypeDto findById(Long id) {
        EventType eventType = eventTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("EventType not found for id => %d", id)));
        return toDto(eventType);
    }

    @Override
    public EventTypeDto findByName(String name) {
        EventType eventType = eventTypeRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("EventType not found for name => %s", name)));
        return toDto(eventType);
    }

    @Override
    public List<EventTypeDto> searchByName(String name) {
        List<EventType> eventTypeList = eventTypeRepository.findEventTypeByName(name);
        List<EventTypeDto> eventTypeDtoList = new ArrayList<>();

        for (EventType eventType : eventTypeList) {
            EventTypeDto eventTypeDto = toDto(eventType);
            eventTypeDtoList.add(eventTypeDto);
        }
        return eventTypeDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        EventType eventType = eventTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("EventType not found for id => %d", id)));
        eventTypeRepository.setStatusInactive(eventType.getId());
    }

    @Override
    @Transactional
    public EventTypeDto update(Long id, EventTypeDto eventTypeDto) {
        EventType existingEventType = eventTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("EventType not found for id => %d", id)));

        existingEventType.setName(eventTypeDto.getName());
        existingEventType.setStatus(eventTypeDto.getStatus());

        existingEventType.setAccount(accountRepository.findById(eventTypeDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", eventTypeDto.getAccount().getId()))));

        EventType updatedEventType = eventTypeRepository.save(existingEventType);
        return toDto(updatedEventType);
    }

    public EventTypeDto toDto(EventType eventType) {
        return EventTypeDto.builder()
                .id(eventType.getId())
                .name(eventType.getName())
                .status(eventType.getStatus())
                .account(eventType.getAccount())
                .build();
    }

    public EventType toEntity(EventTypeDto eventTypeDto) {
        return EventType.builder()
                .id(eventTypeDto.getId())
                .name(eventTypeDto.getName())
                .status(eventTypeDto.getStatus())
                .account(eventTypeDto.getAccount())
                .build();
    }
}
