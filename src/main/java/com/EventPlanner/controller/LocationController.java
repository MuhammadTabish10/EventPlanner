package com.EventPlanner.controller;

import com.EventPlanner.dto.LocationDto;
import com.EventPlanner.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationService.save(locationDto));
    }

    @GetMapping("/location")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<LocationDto>> getAllocations() {
        List<LocationDto> locationDtoList = locationService.getAll();
        return ResponseEntity.ok(locationDtoList);
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
    public ResponseEntity<LocationDto> updateLocation(@PathVariable Long id, @RequestBody LocationDto locationDto) {
        LocationDto updatedLocationDto = locationService.update(id, locationDto);
        return ResponseEntity.ok(updatedLocationDto);
    }
}
