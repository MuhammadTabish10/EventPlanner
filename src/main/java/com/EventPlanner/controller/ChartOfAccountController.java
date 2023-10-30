package com.EventPlanner.controller;

import com.EventPlanner.dto.ChartOfAccountDto;
import com.EventPlanner.service.ChartOfAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ChartOfAccountController {
    private final ChartOfAccountService chartOfAccountService;

    public ChartOfAccountController(ChartOfAccountService chartOfAccountService) {
        this.chartOfAccountService = chartOfAccountService;
    }

    @PostMapping("/chart-of-account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ChartOfAccountDto> createChartOfAccount(@Valid @RequestBody ChartOfAccountDto chartOfAccountDto) {
        return ResponseEntity.ok(chartOfAccountService.save(chartOfAccountDto));
    }

    @GetMapping("/chart-of-account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ChartOfAccountDto>> getAllChartOfAccount() {
        List<ChartOfAccountDto> chartOfAccountDtoList = chartOfAccountService.getAll();
        return ResponseEntity.ok(chartOfAccountDtoList);
    }

    @GetMapping("/chart-of-account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ChartOfAccountDto> getChartOfAccountById(@PathVariable Long id) {
        ChartOfAccountDto chartOfAccountDto = chartOfAccountService.findById(id);
        return ResponseEntity.ok(chartOfAccountDto);
    }

    @GetMapping("/chart-of-account/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ChartOfAccountDto> getChartOfAccountNameByName(@PathVariable String name) {
        ChartOfAccountDto chartOfAccountDto = chartOfAccountService.findByName(name);
        return ResponseEntity.ok(chartOfAccountDto);
    }

    @GetMapping("/chart-of-account/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ChartOfAccountDto>> getAllChartOfAccountByName(@PathVariable String name) {
        List<ChartOfAccountDto> chartOfAccountDtoList = chartOfAccountService.searchByName(name);
        return ResponseEntity.ok(chartOfAccountDtoList);
    }

    @DeleteMapping("/chart-of-account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteChartOfAccount(@PathVariable Long id) {
        chartOfAccountService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/chart-of-account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ChartOfAccountDto> updateChartOfAccount(@PathVariable Long id, @Valid @RequestBody ChartOfAccountDto chartOfAccountDto) {
        ChartOfAccountDto updatedChartOfAccountDto = chartOfAccountService.update(id, chartOfAccountDto);
        return ResponseEntity.ok(updatedChartOfAccountDto);
    }
}
