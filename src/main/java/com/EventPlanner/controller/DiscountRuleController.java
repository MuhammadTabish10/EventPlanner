package com.EventPlanner.controller;

import com.EventPlanner.dto.DiscountRuleDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.DiscountRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DiscountRuleController {
    private final DiscountRuleService discountRuleService;

    public DiscountRuleController(DiscountRuleService discountRuleService) {
        this.discountRuleService = discountRuleService;
    }

    @PostMapping("/discount-rule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DiscountRuleDto> createDiscountRule(@Valid @RequestBody DiscountRuleDto discountRuleDto) {
        return ResponseEntity.ok(discountRuleService.save(discountRuleDto));
    }

    @GetMapping("/discount-rule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DiscountRuleDto>> getAllDiscountRule() {
        List<DiscountRuleDto> discountRuleDtoList = discountRuleService.getAll();
        return ResponseEntity.ok(discountRuleDtoList);
    }

    @GetMapping("/discount-rule/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedDiscountRule(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = discountRuleService.getAllPaginatedDiscountRule(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/discount-rule/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DiscountRuleDto> getDiscountRuleById(@PathVariable Long id) {
        DiscountRuleDto discountRuleDto = discountRuleService.findById(id);
        return ResponseEntity.ok(discountRuleDto);
    }

    @GetMapping("/discount-rule/code/{code}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllDiscountRuleByDiscountCode(
            @PathVariable String code,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = discountRuleService.searchByDiscountCode(code, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/discount-rule/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDiscountRule(@PathVariable Long id) {
        discountRuleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/discount-rule/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DiscountRuleDto> updateDiscountRule(@PathVariable Long id, @Valid @RequestBody DiscountRuleDto discountRuleDto) {
        DiscountRuleDto updatedDiscountRuleDto = discountRuleService.update(id, discountRuleDto);
        return ResponseEntity.ok(updatedDiscountRuleDto);
    }
}
