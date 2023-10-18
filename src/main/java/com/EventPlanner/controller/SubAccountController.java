package com.EventPlanner.controller;

import com.EventPlanner.dto.SubAccountDto;
import com.EventPlanner.model.SubAccountPK;
import com.EventPlanner.service.SubAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SubAccountDto> createSubAccount(@RequestBody SubAccountDto subAccountDto) {
        return ResponseEntity.ok(subAccountService.save(subAccountDto));
    }

    @GetMapping("/sub-account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SubAccountDto>> getAllSubAccounts() {
        List<SubAccountDto> subAccountDtoList = subAccountService.getAll();
        return ResponseEntity.ok(subAccountDtoList);
    }

    @GetMapping("/sub-account/account/{id}/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SubAccountDto> getSubAccountById(@PathVariable Long id, @PathVariable String name) {
        SubAccountPK subAccountPK = new SubAccountPK(id,name);
        SubAccountDto subAccountDto = subAccountService.findById(subAccountPK);
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
    public ResponseEntity<List<SubAccountDto>> getAllSubAccountsByName(@PathVariable String name) {
        List<SubAccountDto> subAccountDtoList = subAccountService.searchByName(name);
        return ResponseEntity.ok(subAccountDtoList);
    }

    @DeleteMapping("/sub-account/account/{id}/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id, @PathVariable String name) {
        SubAccountPK subAccountPK = new SubAccountPK(id,name);
        subAccountService.deleteById(subAccountPK);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sub-account/account/{id}/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SubAccountDto> updateSubAccount(@PathVariable Long id, @PathVariable String name, @RequestBody SubAccountDto subAccountDto) {
        SubAccountPK subAccountPK = new SubAccountPK(id,name);
        SubAccountDto updatedSubAccountDto = subAccountService.update(subAccountPK, subAccountDto);
        return ResponseEntity.ok(updatedSubAccountDto);
    }
}
