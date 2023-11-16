package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SessionDto;
import com.EventPlanner.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @PostMapping("/session")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SessionDto> createSession(@Valid @RequestBody SessionDto sessionDto) {
        return ResponseEntity.ok(sessionService.save(sessionDto));
    }

    @GetMapping("/session")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SessionDto>> getAllSession() {
        List<SessionDto> sessionDtoList = sessionService.getAll();
        return ResponseEntity.ok(sessionDtoList);
    }

    @GetMapping("/session/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedVenue(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = sessionService.getAllPaginatedSession(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/session/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SessionDto> getSessionById(@PathVariable Long id) {
        SessionDto sessionDto = sessionService.findById(id);
        return ResponseEntity.ok(sessionDto);
    }

    @GetMapping("/session/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SessionDto> getSessionByName(@PathVariable String name) {
        SessionDto sessionDto = sessionService.findByName(name);
        return ResponseEntity.ok(sessionDto);
    }

    @GetMapping("/session/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllSessionByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = sessionService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/session/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/session/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SessionDto> updateSession(@PathVariable Long id, @Valid @RequestBody SessionDto sessionDto) {
        SessionDto updatedSessionDto = sessionService.update(id, sessionDto);
        return ResponseEntity.ok(updatedSessionDto);
    }
}
