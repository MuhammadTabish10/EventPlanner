package com.EventPlanner.controller;

import com.EventPlanner.dto.ProvinceDto;
import com.EventPlanner.service.ProvinceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProvinceDto> createProvince(@RequestBody ProvinceDto provinceDto) {
        return ResponseEntity.ok(provinceService.save(provinceDto));
    }

    @GetMapping("/province")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProvinceDto>> getAllProvince() {
        List<ProvinceDto> provinceDtoList = provinceService.getAll();
        return ResponseEntity.ok(provinceDtoList);
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
    public ResponseEntity<List<ProvinceDto>> getAllProvinceByName(@PathVariable String name) {
        List<ProvinceDto> provinceDtoList = provinceService.searchByName(name);
        return ResponseEntity.ok(provinceDtoList);
    }

    @DeleteMapping("/province/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        provinceService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/province/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProvinceDto> updateProvince(@PathVariable Long id, @RequestBody ProvinceDto provinceDto) {
        ProvinceDto updatedProvinceDto = provinceService.update(id, provinceDto);
        return ResponseEntity.ok(updatedProvinceDto);
    }
}
