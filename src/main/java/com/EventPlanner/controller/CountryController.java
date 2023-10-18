package com.EventPlanner.controller;

import com.EventPlanner.dto.CountryDto;
import com.EventPlanner.dto.ProvinceDto;
import com.EventPlanner.model.Country;
import com.EventPlanner.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/country")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto) {
        return ResponseEntity.ok(countryService.save(countryDto));
    }

    @GetMapping("/country")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CountryDto>> getAllCountry() {
        List<CountryDto> countryDtoList = countryService.getAll();
        return ResponseEntity.ok(countryDtoList);
    }

    @GetMapping("/country/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        CountryDto countryDto = countryService.findById(id);
        return ResponseEntity.ok(countryDto);
    }

    @GetMapping("/country/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CountryDto> getCountryByName(@PathVariable String name) {
        CountryDto countryDto = countryService.findByName(name);
        return ResponseEntity.ok(countryDto);
    }

    @GetMapping("/country/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CountryDto>> getAllCountryByName(@PathVariable String name) {
        List<CountryDto> countryDtoList = countryService.searchByName(name);
        return ResponseEntity.ok(countryDtoList);
    }

    @DeleteMapping("/country/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/country/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        CountryDto updatedCountryDto = countryService.update(id, countryDto);
        return ResponseEntity.ok(updatedCountryDto);
    }
}
