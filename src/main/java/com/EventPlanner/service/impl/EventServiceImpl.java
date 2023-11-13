package com.EventPlanner.service.impl;

import com.EventPlanner.dto.EventDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.EventType;
import com.EventPlanner.model.Venue;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.EventRepository;
import com.EventPlanner.repository.EventTypeRepository;
import com.EventPlanner.repository.VenueRepository;
import com.EventPlanner.service.EventService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final AccountRepository accountRepository;
    private final VenueRepository venueRepository;

    public EventServiceImpl(EventRepository eventRepository, EventTypeRepository eventTypeRepository, AccountRepository accountRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.accountRepository = accountRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    @Transactional
    public EventDto save(EventDto eventDto) {
        Event event = toEntity(eventDto);
        event.setStatus(true);

        EventType eventType = eventTypeRepository.findById(event.getEventType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event Type not found for id => %d", event.getEventType().getId())));

        Venue venue = venueRepository.findById(event.getVenue().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", event.getVenue().getId())));

        Account account = accountRepository.findById(event.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", event.getAccount().getId())));

        event.setEventType(eventType);
        event.setVenue(venue);
        event.setAccount(account);
        Event createdEvent = eventRepository.save(event);
        return toDto(createdEvent);
    }

    @Override
    public List<EventDto> getAll() {
        List<Event> eventList = eventRepository.findAllInDesOrderByIdAndStatus();
        List<EventDto> eventDtoList = new ArrayList<>();

        for (Event event : eventList) {
            EventDto eventDto = toDto(event);
            eventDtoList.add(eventDto);
        }
        return eventDtoList;
    }

    @Override
    public EventDto findById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", id)));
        return toDto(event);
    }

    @Override
    public EventDto findByName(String name) {
        Event event = eventRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for name => %s", name)));
        return toDto(event);
    }

    @Override
    public List<EventDto> searchByName(String name) {
        List<Event> eventList = eventRepository.findEventByName(name);
        List<EventDto> eventDtoList = new ArrayList<>();

        for (Event event : eventList) {
            EventDto eventDto = toDto(event);
            eventDtoList.add(eventDto);
        }
        return eventDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", id)));
        eventRepository.setStatusInactive(event.getId());
    }

    @Override
    @Transactional
    public EventDto update(Long id, EventDto eventDto) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", id)));

        existingEvent.setName(eventDto.getName());
        existingEvent.setEventStatus(eventDto.getEventStatus());
        existingEvent.setMaxRegistrations(eventDto.getMaxRegistrations());
        existingEvent.setStartDateAndTime(eventDto.getStartDateAndTime());
        existingEvent.setEndDateAndTime(eventDto.getEndDateAndTime());
        existingEvent.setRegistrationOpenDate(eventDto.getRegistrationOpenDate());
        existingEvent.setEventLead(eventDto.getEventLead());
        existingEvent.setEventClientContact(eventDto.getEventClientContact());

        existingEvent.setEventType(eventTypeRepository.findById(eventDto.getEventType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("EventType not found for id => %d", eventDto.getEventType().getId()))));

        existingEvent.setVenue(venueRepository.findById(eventDto.getVenue().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", eventDto.getVenue().getId()))));

        existingEvent.setAccount(accountRepository.findById(eventDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", eventDto.getAccount().getId()))));

        Event updatedEvent = eventRepository.save(existingEvent);
        return toDto(updatedEvent);
    }

    public EventDto toDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .eventStatus(event.getEventStatus())
                .maxRegistrations(event.getMaxRegistrations())
                .startDateAndTime(event.getStartDateAndTime())
                .endDateAndTime(event.getEndDateAndTime())
                .registrationOpenDate(event.getRegistrationOpenDate())
                .eventLead(event.getEventLead())
                .eventClientContact(event.getEventClientContact())
                .status(event.getStatus())
                .eventType(event.getEventType())
                .venue(event.getVenue())
                .account(event.getAccount())
                .build();
    }

    public Event toEntity(EventDto eventDto) {
        return Event.builder()
                .id(eventDto.getId())
                .name(eventDto.getName())
                .eventStatus(eventDto.getEventStatus())
                .maxRegistrations(eventDto.getMaxRegistrations())
                .startDateAndTime(eventDto.getStartDateAndTime())
                .endDateAndTime(eventDto.getEndDateAndTime())
                .registrationOpenDate(eventDto.getRegistrationOpenDate())
                .eventLead(eventDto.getEventLead())
                .eventClientContact(eventDto.getEventClientContact())
                .status(eventDto.getStatus())
                .eventType(eventDto.getEventType())
                .venue(eventDto.getVenue())
                .account(eventDto.getAccount())
                .build();
    }
}
