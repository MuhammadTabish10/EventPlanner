package com.EventPlanner.controller;

import com.EventPlanner.dto.CurrencyDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/currency")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CurrencyDto> createCurrency(@Valid @RequestBody CurrencyDto currencyDto) {
        return ResponseEntity.ok(currencyService.save(currencyDto));
    }

    @GetMapping("/currency")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        List<CurrencyDto> currencyDtoList = currencyService.getAll();
        return ResponseEntity.ok(currencyDtoList);
    }

    @GetMapping("/currency/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedCountry(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = currencyService.getAllPaginatedCurrency(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/currency/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CurrencyDto> getCurrencyById(@PathVariable Long id) {
        CurrencyDto currencyDto = currencyService.findById(id);
        return ResponseEntity.ok(currencyDto);
    }

    @GetMapping("/currency/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CurrencyDto> getCurrencyByName(@PathVariable String name) {
        CurrencyDto currencyDto = currencyService.findByName(name);
        return ResponseEntity.ok(currencyDto);
    }

    @GetMapping("/currency/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllCurrenciesByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = currencyService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/currency/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        currencyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/currency/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CurrencyDto> updateCurrency(@PathVariable Long id, @Valid @RequestBody CurrencyDto currencyDto) {
        CurrencyDto updatedCurrencyDto = currencyService.update(id, currencyDto);
        return ResponseEntity.ok(updatedCurrencyDto);
    }
}
