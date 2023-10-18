package com.EventPlanner.controller;

import com.EventPlanner.dto.CurrencyDto;
import com.EventPlanner.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CurrencyDto> createCurrency(@RequestBody CurrencyDto currencyDto) {
        return ResponseEntity.ok(currencyService.save(currencyDto));
    }

    @GetMapping("/currency")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        List<CurrencyDto> currencyDtoList = currencyService.getAll();
        return ResponseEntity.ok(currencyDtoList);
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
    public ResponseEntity<List<CurrencyDto>> getAllCurrenciesByName(@PathVariable String name) {
        List<CurrencyDto> currencyDtoList = currencyService.searchByName(name);
        return ResponseEntity.ok(currencyDtoList);
    }

        @DeleteMapping("/currency/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        currencyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/currency/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CurrencyDto> updateCurrency(@PathVariable Long id, @RequestBody CurrencyDto currencyDto) {
        CurrencyDto updatedCurrencyDto = currencyService.update(id, currencyDto);
        return ResponseEntity.ok(updatedCurrencyDto);
    }
}
