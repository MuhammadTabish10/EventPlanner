package com.EventPlanner.controller;

import com.EventPlanner.dto.EventDto;
import com.EventPlanner.dto.PaginationResponse;
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

    @GetMapping("/event/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedEvent(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = eventService.getAllPaginatedEvent(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
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
    public ResponseEntity<PaginationResponse> getAllEventByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = eventService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
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
