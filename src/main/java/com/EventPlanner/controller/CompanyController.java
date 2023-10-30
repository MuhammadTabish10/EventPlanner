package com.EventPlanner.controller;

import com.EventPlanner.dto.CompanyDto;
import com.EventPlanner.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok(companyService.save(companyDto));
    }

    @GetMapping("/company")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CompanyDto>> getAllCompany() {
        List<CompanyDto> companyDtoList = companyService.getAll();
        return ResponseEntity.ok(companyDtoList);
    }

    @GetMapping("/company/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id) {
        CompanyDto companyDto = companyService.findById(id);
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping("/company/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CompanyDto> getCompanyNameByName(@PathVariable String name) {
        CompanyDto companyDto = companyService.findByName(name);
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping("/company/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CompanyDto>> getAllCompanyByName(@PathVariable String name) {
        List<CompanyDto> companyDtoList = companyService.searchByName(name);
        return ResponseEntity.ok(companyDtoList);
    }

    @DeleteMapping("/company/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/company/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyDto companyDto) {
        CompanyDto updatedCompanyDto = companyService.update(id, companyDto);
        return ResponseEntity.ok(updatedCompanyDto);
    }
}
