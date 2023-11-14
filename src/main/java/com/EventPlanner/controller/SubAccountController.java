package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SubAccountDto;
import com.EventPlanner.service.SubAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SubAccountController {
    private final SubAccountService subAccountService;

    public SubAccountController(SubAccountService subAccountService) {
        this.subAccountService = subAccountService;
    }

    @PostMapping("/sub-account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SubAccountDto> createSubAccount(@Valid @RequestBody SubAccountDto subAccountDto) {
        return ResponseEntity.ok(subAccountService.save(subAccountDto));
    }

    @GetMapping("/sub-account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SubAccountDto>> getAllSubAccounts() {
        List<SubAccountDto> subAccountDtoList = subAccountService.getAll();
        return ResponseEntity.ok(subAccountDtoList);
    }

    @GetMapping("/sub-account/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedSubAccount(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = subAccountService.getAllPaginatedSubAccount(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/sub-account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SubAccountDto> getSubAccountById(@PathVariable Long id) {
        SubAccountDto subAccountDto = subAccountService.findById(id);
        return ResponseEntity.ok(subAccountDto);
    }

    @GetMapping("/sub-account/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SubAccountDto> getSubAccountByName(@PathVariable String name) {
        SubAccountDto subAccountDto = subAccountService.findByName(name);
        return ResponseEntity.ok(subAccountDto);
    }

    @GetMapping("/sub-account/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllSubAccountsByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = subAccountService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }


    @DeleteMapping("/sub-account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        subAccountService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sub-account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SubAccountDto> updateSubAccount(@PathVariable Long id, @Valid @RequestBody SubAccountDto subAccountDto) {
        SubAccountDto updatedSubAccountDto = subAccountService.update(id, subAccountDto);
        return ResponseEntity.ok(updatedSubAccountDto);
    }
}
