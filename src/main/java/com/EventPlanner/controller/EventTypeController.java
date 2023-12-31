package com.EventPlanner.controller;

import com.EventPlanner.dto.EventTypeDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.EventTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventTypeController {
    private final EventTypeService eventTypeService;

    public EventTypeController(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @PostMapping("/event-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventTypeDto> createEventType(@Valid @RequestBody EventTypeDto eventTypeDto) {
        return ResponseEntity.ok(eventTypeService.save(eventTypeDto));
    }

    @GetMapping("/event-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EventTypeDto>> getAllEventTypes() {
        List<EventTypeDto> eventTypeDtoList = eventTypeService.getAll();
        return ResponseEntity.ok(eventTypeDtoList);
    }

    @GetMapping("/event-type/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedEventType(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = eventTypeService.getAllPaginatedEventType(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/event-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventTypeDto> getEventTypeById(@PathVariable Long id) {
        EventTypeDto eventTypeDto = eventTypeService.findById(id);
        return ResponseEntity.ok(eventTypeDto);
    }

    @GetMapping("/event-type/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventTypeDto> getEventTypeByName(@PathVariable String name) {
        EventTypeDto eventTypeDto = eventTypeService.findByName(name);
        return ResponseEntity.ok(eventTypeDto);
    }

    @GetMapping("/event-type/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllEventTypesByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = eventTypeService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/event-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEventType(@PathVariable Long id) {
        eventTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/event-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventTypeDto> updateEventType(@PathVariable Long id, @Valid @RequestBody EventTypeDto eventTypeDto) {
        EventTypeDto updatedEventTypeDto = eventTypeService.update(id, eventTypeDto);
        return ResponseEntity.ok(updatedEventTypeDto);
    }
}
