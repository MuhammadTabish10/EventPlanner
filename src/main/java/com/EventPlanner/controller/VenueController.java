package com.EventPlanner.controller;

import com.EventPlanner.dto.TagDto;
import com.EventPlanner.dto.VenueDto;
import com.EventPlanner.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping("/venue")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VenueDto> createVenue(@Valid @RequestBody VenueDto venueDto) {
        return ResponseEntity.ok(venueService.save(venueDto));
    }

    @GetMapping("/venue")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<VenueDto>> getAllVenue() {
        List<VenueDto> venueDtoList = venueService.getAll();
        return ResponseEntity.ok(venueDtoList);
    }

    @GetMapping("/venue/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VenueDto> getVenueById(@PathVariable Long id) {
        VenueDto venueDto = venueService.findById(id);
        return ResponseEntity.ok(venueDto);
    }

    @GetMapping("/venue/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VenueDto> getVenueByName(@PathVariable String name) {
        VenueDto venueDto = venueService.findByName(name);
        return ResponseEntity.ok(venueDto);
    }

    @GetMapping("/venue/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<VenueDto>> getAllVenueByName(@PathVariable String name) {
        List<VenueDto> venueDtoList = venueService.searchByName(name);
        return ResponseEntity.ok(venueDtoList);
    }

    @DeleteMapping("/venue/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/venue/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VenueDto> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueDto venueDto) {
        VenueDto updatedVenueDto = venueService.update(id, venueDto);
        return ResponseEntity.ok(updatedVenueDto);
    }
}
