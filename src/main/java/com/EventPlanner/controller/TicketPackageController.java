package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.TicketPackageDto;
import com.EventPlanner.service.TicketPackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketPackageController {
    private final TicketPackageService ticketPackageService;

    public TicketPackageController(TicketPackageService ticketPackageService) {
        this.ticketPackageService = ticketPackageService;
    }

    @PostMapping("/ticket-package")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketPackageDto> createTicketPackage(@Valid @RequestBody TicketPackageDto ticketPackageDto) {
        return ResponseEntity.ok(ticketPackageService.save(ticketPackageDto));
    }

    @GetMapping("/ticket-package")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TicketPackageDto>> getAllTicketPackage() {
        List<TicketPackageDto> ticketPackageDtoList = ticketPackageService.getAll();
        return ResponseEntity.ok(ticketPackageDtoList);
    }

    @GetMapping("/ticket-package/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedTicketPackage(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = ticketPackageService.getAllPaginatedTicketPackage(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/ticket-package/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketPackageDto> getTicketPackageById(@PathVariable Long id) {
        TicketPackageDto ticketPackageDto = ticketPackageService.findById(id);
        return ResponseEntity.ok(ticketPackageDto);
    }

    @GetMapping("/ticket-package/types/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllTicketPackageByType(
            @PathVariable String type,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = ticketPackageService.searchByComboType(type, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/ticket-package/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTicketPackage(@PathVariable Long id) {
        ticketPackageService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ticket-package/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketPackageDto> updateTicketPackage(@PathVariable Long id, @Valid @RequestBody TicketPackageDto ticketPackageDto) {
        TicketPackageDto updatedTicketPackageDto = ticketPackageService.update(id, ticketPackageDto);
        return ResponseEntity.ok(updatedTicketPackageDto);
    }
}
