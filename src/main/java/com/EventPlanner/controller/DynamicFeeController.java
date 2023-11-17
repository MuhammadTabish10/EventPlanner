package com.EventPlanner.controller;

import com.EventPlanner.dto.DynamicFeeDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.DynamicFeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DynamicFeeController {
    private final DynamicFeeService dynamicFeeService;

    public DynamicFeeController(DynamicFeeService dynamicFeeService) {
        this.dynamicFeeService = dynamicFeeService;
    }

    @PostMapping("/dynamic-fee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DynamicFeeDto> createDynamicFee(@Valid @RequestBody DynamicFeeDto dynamicFeeDto) {
        return ResponseEntity.ok(dynamicFeeService.save(dynamicFeeDto));
    }

    @GetMapping("/dynamic-fee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DynamicFeeDto>> getAllDynamicFee() {
        List<DynamicFeeDto> dynamicFeeDtoList = dynamicFeeService.getAll();
        return ResponseEntity.ok(dynamicFeeDtoList);
    }

    @GetMapping("/dynamic-fee/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedDynamicFee(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = dynamicFeeService.getAllPaginatedDynamicFee(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/dynamic-fee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DynamicFeeDto> getDynamicFeeById(@PathVariable Long id) {
        DynamicFeeDto dynamicFeeDto = dynamicFeeService.findById(id);
        return ResponseEntity.ok(dynamicFeeDto);
    }

    @GetMapping("/dynamic-fee/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DynamicFeeDto> getDynamicFeeByName(@PathVariable String name) {
        DynamicFeeDto dynamicFeeDto = dynamicFeeService.findByTicketName(name);
        return ResponseEntity.ok(dynamicFeeDto);
    }

    @GetMapping("/dynamic-fee/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllDynamicFeeByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = dynamicFeeService.searchByTicketName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/dynamic-fee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDynamicFee(@PathVariable Long id) {
        dynamicFeeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/dynamic-fee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DynamicFeeDto> updateDynamicFee(@PathVariable Long id, @Valid @RequestBody DynamicFeeDto dynamicFeeDto) {
        DynamicFeeDto updatedDynamicFeeDto = dynamicFeeService.update(id, dynamicFeeDto);
        return ResponseEntity.ok(updatedDynamicFeeDto);
    }
}
