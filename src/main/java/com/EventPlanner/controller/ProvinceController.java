package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.ProvinceDto;
import com.EventPlanner.service.ProvinceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProvinceController {
    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping("/province")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProvinceDto> createProvince(@Valid @RequestBody ProvinceDto provinceDto) {
        return ResponseEntity.ok(provinceService.save(provinceDto));
    }

    @GetMapping("/province")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProvinceDto>> getAllProvince() {
        List<ProvinceDto> provinceDtoList = provinceService.getAll();
        return ResponseEntity.ok(provinceDtoList);
    }

    @GetMapping("/province/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedProvince(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = provinceService.getAllPaginatedProvince(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/province/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProvinceDto> getProvinceById(@PathVariable Long id) {
        ProvinceDto provinceDto = provinceService.findById(id);
        return ResponseEntity.ok(provinceDto);
    }

    @GetMapping("/province/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProvinceDto> getProvinceByName(@PathVariable String name) {
        ProvinceDto provinceDto = provinceService.findByName(name);
        return ResponseEntity.ok(provinceDto);
    }

    @GetMapping("/province/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllProvinceByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = provinceService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/province/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        provinceService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/province/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProvinceDto> updateProvince(@PathVariable Long id, @Valid @RequestBody ProvinceDto provinceDto) {
        ProvinceDto updatedProvinceDto = provinceService.update(id, provinceDto);
        return ResponseEntity.ok(updatedProvinceDto);
    }
}
