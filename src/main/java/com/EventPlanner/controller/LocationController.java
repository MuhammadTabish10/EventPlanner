package com.EventPlanner.controller;

import com.EventPlanner.dto.LocationDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/location")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationService.save(locationDto));
    }

    @GetMapping("/location")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<LocationDto>> getAllocations() {
        List<LocationDto> locationDtoList = locationService.getAll();
        return ResponseEntity.ok(locationDtoList);
    }

    @GetMapping("/location/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedLocation(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = locationService.getAllPaginatedLocation(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/location/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        LocationDto locationDto = locationService.findById(id);
        return ResponseEntity.ok(locationDto);
    }

    @DeleteMapping("/location/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/location/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable Long id, @Valid @RequestBody LocationDto locationDto) {
        LocationDto updatedLocationDto = locationService.update(id, locationDto);
        return ResponseEntity.ok(updatedLocationDto);
    }
}
