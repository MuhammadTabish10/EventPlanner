package com.EventPlanner.controller;

import com.EventPlanner.dto.IndustryDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.IndustryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IndustryController {
    private final IndustryService industryService;

    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    @PostMapping("/industry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<IndustryDto> createIndustry(@Valid @RequestBody IndustryDto industryDto) {
        return ResponseEntity.ok(industryService.save(industryDto));
    }

    @GetMapping("/industry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<IndustryDto>> getAllIndustries() {
        List<IndustryDto> industryDtoList = industryService.getAll();
        return ResponseEntity.ok(industryDtoList);
    }

    @GetMapping("/industry/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedIndustry(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = industryService.getAllPaginatedIndustry(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/industry/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<IndustryDto> getIndustryById(@PathVariable Long id) {
        IndustryDto industryDto = industryService.findById(id);
        return ResponseEntity.ok(industryDto);
    }

    @GetMapping("/industry/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<IndustryDto> getIndustryByName(@PathVariable String name) {
        IndustryDto industryDto = industryService.findByName(name);
        return ResponseEntity.ok(industryDto);
    }

    @GetMapping("/industry/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllIndustryByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = industryService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/industry/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        industryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/industry/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<IndustryDto> updateIndustry(@PathVariable Long id, @Valid @RequestBody IndustryDto industryDto) {
        IndustryDto updatedIndustryDto = industryService.update(id, industryDto);
        return ResponseEntity.ok(updatedIndustryDto);
    }
}
