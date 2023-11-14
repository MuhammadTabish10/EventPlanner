package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TicketTypeDto;
import com.EventPlanner.service.TicketTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @PostMapping("/ticket-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketTypeDto> createTicketType(@Valid @RequestBody TicketTypeDto ticketTypeDto) {
        return ResponseEntity.ok(ticketTypeService.save(ticketTypeDto));
    }

    @GetMapping("/ticket-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TicketTypeDto>> getAllTicketType() {
        List<TicketTypeDto> ticketTypeDtoList = ticketTypeService.getAll();
        return ResponseEntity.ok(ticketTypeDtoList);
    }

    @GetMapping("/ticket-type/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedTicketType(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = ticketTypeService.getAllPaginatedTicketType(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/ticket-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketTypeDto> getTicketTypeById(@PathVariable Long id) {
        TicketTypeDto ticketTypeDto = ticketTypeService.findById(id);
        return ResponseEntity.ok(ticketTypeDto);
    }

    @GetMapping("/ticket-type/type/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketTypeDto> getTicketTypeByType(@PathVariable String type) {
        TicketTypeDto ticketTypeDto = ticketTypeService.findByType(type);
        return ResponseEntity.ok(ticketTypeDto);
    }

    @GetMapping("/ticket-type/types/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllTicketTypeByType(
            @PathVariable String type,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = ticketTypeService.searchByType(type, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/ticket-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTicketType(@PathVariable Long id) {
        ticketTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ticket-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketTypeDto> updateTicketType(@PathVariable Long id, @Valid @RequestBody TicketTypeDto ticketTypeDto) {
        TicketTypeDto updatedTicketTypeDto = ticketTypeService.update(id, ticketTypeDto);
        return ResponseEntity.ok(updatedTicketTypeDto);
    }
}
