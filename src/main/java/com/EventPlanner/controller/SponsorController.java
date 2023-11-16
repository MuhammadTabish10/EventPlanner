package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SponsorDto;
import com.EventPlanner.service.SponsorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SponsorController {
    private final SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @PostMapping("/sponsor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SponsorDto> createSponsor(@Valid @RequestBody SponsorDto sponsorDto) {
        return ResponseEntity.ok(sponsorService.save(sponsorDto));
    }

    @GetMapping("/sponsor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SponsorDto>> getAllSponsor() {
        List<SponsorDto> sponsorDtoList = sponsorService.getAll();
        return ResponseEntity.ok(sponsorDtoList);
    }

    @GetMapping("/sponsor/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedSponsor(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = sponsorService.getAllPaginatedSponsor(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/sponsor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SponsorDto> getSponsorById(@PathVariable Long id) {
        SponsorDto sponsorDto = sponsorService.findById(id);
        return ResponseEntity.ok(sponsorDto);
    }

    @GetMapping("/sponsor/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllSponsorByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = sponsorService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/sponsor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long id) {
        sponsorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sponsor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SponsorDto> updateSponsor(@PathVariable Long id, @Valid @RequestBody SponsorDto sponsorDto) {
        SponsorDto updatedSponsorDto = sponsorService.update(id, sponsorDto);
        return ResponseEntity.ok(updatedSponsorDto);
    }
}
