package com.EventPlanner.controller;

import com.EventPlanner.dto.CompanyTypeDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.CompanyTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyTypeController {
    private final CompanyTypeService companyTypeService;

    public CompanyTypeController(CompanyTypeService companyTypeService) {
        this.companyTypeService = companyTypeService;
    }

    @PostMapping("/company-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CompanyTypeDto> createCompanyType(@Valid @RequestBody CompanyTypeDto companyTypeDto) {
        return ResponseEntity.ok(companyTypeService.save(companyTypeDto));
    }

    @GetMapping("/company-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CompanyTypeDto>> getAllCompanyType() {
        List<CompanyTypeDto> companyTypeDtoList = companyTypeService.getAll();
        return ResponseEntity.ok(companyTypeDtoList);
    }

    @GetMapping("/company-type/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedCompanyType(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = companyTypeService.getAllPaginatedCompanyType(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }


    @GetMapping("/company-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CompanyTypeDto> getCompanyTypeById(@PathVariable Long id) {
        CompanyTypeDto companyTypeDto = companyTypeService.findById(id);
        return ResponseEntity.ok(companyTypeDto);
    }

    @GetMapping("/company-type/type/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CompanyTypeDto> getCompanyTypeByType(@PathVariable String type) {
        CompanyTypeDto companyTypeDto = companyTypeService.findByType(type);
        return ResponseEntity.ok(companyTypeDto);
    }

    @GetMapping("/company-type/types/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllCompanyTypeByType(
            @PathVariable String type,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = companyTypeService.searchByType(type, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/company-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCompanyType(@PathVariable Long id) {
        companyTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/company-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CompanyTypeDto> updateCompanyType(@PathVariable Long id, @Valid @RequestBody CompanyTypeDto companyTypeDto) {
        CompanyTypeDto updatedCompanyTypeDto = companyTypeService.update(id, companyTypeDto);
        return ResponseEntity.ok(updatedCompanyTypeDto);
    }
}
