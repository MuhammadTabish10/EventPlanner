package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.StandardPriceDto;
import com.EventPlanner.dto.TagDto;
import com.EventPlanner.service.StandardPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StandardPriceController {
    private final StandardPriceService standardPriceService;

    public StandardPriceController(StandardPriceService standardPriceService) {
        this.standardPriceService = standardPriceService;
    }

    @PostMapping("/standard-price")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardPriceDto> createStandardPrice(@Valid @RequestBody StandardPriceDto standardPriceDto) {
        return ResponseEntity.ok(standardPriceService.save(standardPriceDto));
    }

    @GetMapping("/standard-price")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<StandardPriceDto>> getAllStandardPrice() {
        List<StandardPriceDto> standardPriceDtoList = standardPriceService.getAll();
        return ResponseEntity.ok(standardPriceDtoList);
    }

    @GetMapping("/standard-price/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedStandardPrice(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = standardPriceService.getAllPaginatedStandardPrice(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/standard-price/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardPriceDto> getStandardPriceById(@PathVariable Long id) {
        StandardPriceDto standardPriceDto = standardPriceService.findById(id);
        return ResponseEntity.ok(standardPriceDto);
    }

    @GetMapping("/standard-price/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardPriceDto> getStandardPriceByName(@PathVariable String name) {
        StandardPriceDto standardPriceDto = standardPriceService.findByTicketName(name);
        return ResponseEntity.ok(standardPriceDto);
    }

    @GetMapping("/standard-price/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllStandardPriceByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = standardPriceService.searchByTicketName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/standard-price/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteStandardPrice(@PathVariable Long id) {
        standardPriceService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/standard-price/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardPriceDto> updateStandardPrice(@PathVariable Long id, @Valid @RequestBody StandardPriceDto standardPriceDto) {
        StandardPriceDto updatedStandardPriceDto = standardPriceService.update(id, standardPriceDto);
        return ResponseEntity.ok(updatedStandardPriceDto);
    }
}
