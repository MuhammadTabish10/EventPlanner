package com.EventPlanner.controller;

import com.EventPlanner.dto.EventDto;
import com.EventPlanner.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/event")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto eventDto) {
        return ResponseEntity.ok(eventService.save(eventDto));
    }

    @GetMapping("/event")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> eventDtoList = eventService.getAll();
        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping("/event/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        EventDto eventDto = eventService.findById(id);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/event/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDto> getEventByName(@PathVariable String name) {
        EventDto eventDto = eventService.findByName(name);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/event/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EventDto>> getAllEventByName(@PathVariable String name) {
        List<EventDto> eventDtoList = eventService.searchByName(name);
        return ResponseEntity.ok(eventDtoList);
    }

    @DeleteMapping("/event/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/event/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDto eventDto) {
        EventDto updatedEventDto = eventService.update(id, eventDto);
        return ResponseEntity.ok(updatedEventDto);
    }
}
