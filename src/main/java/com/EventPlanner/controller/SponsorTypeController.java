package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SponsorTypeDto;
import com.EventPlanner.service.SponsorTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SponsorTypeController {
    private final SponsorTypeService sponsorTypeService;

    public SponsorTypeController(SponsorTypeService sponsorTypeService) {
        this.sponsorTypeService = sponsorTypeService;
    }

    @PostMapping("/sponsor-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SponsorTypeDto> createSponsorType(@Valid @RequestBody SponsorTypeDto sponsorTypeDto) {
        return ResponseEntity.ok(sponsorTypeService.save(sponsorTypeDto));
    }

    @GetMapping("/sponsor-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SponsorTypeDto>> getAllSponsorType() {
        List<SponsorTypeDto> sponsorTypeDtoList = sponsorTypeService.getAll();
        return ResponseEntity.ok(sponsorTypeDtoList);
    }

    @GetMapping("/sponsor-type/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedSponsorType(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = sponsorTypeService.getAllPaginatedSponsorType(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/sponsor-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SponsorTypeDto> getSponsorTypeById(@PathVariable Long id) {
        SponsorTypeDto sponsorTypeDto = sponsorTypeService.findById(id);
        return ResponseEntity.ok(sponsorTypeDto);
    }

    @GetMapping("/sponsor-type/type/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SponsorTypeDto> getSponsorTypeByType(@PathVariable String type) {
        SponsorTypeDto sponsorTypeDto = sponsorTypeService.findByType(type);
        return ResponseEntity.ok(sponsorTypeDto);
    }

    @GetMapping("/sponsor-type/types/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllSponsorTypeByType(
            @PathVariable String type,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = sponsorTypeService.searchByType(type, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }


    @DeleteMapping("/sponsor-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSponsorType(@PathVariable Long id) {
        sponsorTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sponsor-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SponsorTypeDto> updateSponsorType(@PathVariable Long id, @Valid @RequestBody SponsorTypeDto sponsorTypeDto) {
        SponsorTypeDto updatedSponsorTypeDto = sponsorTypeService.update(id, sponsorTypeDto);
        return ResponseEntity.ok(updatedSponsorTypeDto);
    }
}
