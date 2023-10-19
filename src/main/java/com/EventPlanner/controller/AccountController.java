package com.EventPlanner.controller;

import com.EventPlanner.dto.AccountDto;
import com.EventPlanner.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.save(accountDto));
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accountDtoList = accountService.getAll();
        return ResponseEntity.ok(accountDtoList);
    }

    @GetMapping("/account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.findById(id);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/account/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDto> getAccountByName(@PathVariable String name) {
        AccountDto accountDto = accountService.findByName(name);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/account/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AccountDto>> getAllAccountsByName(@PathVariable String name) {
        List<AccountDto> accountDtoList = accountService.searchByName(name);
        return ResponseEntity.ok(accountDtoList);
    }

    @DeleteMapping("/account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/account/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto accountDto) {
        AccountDto updatedAccountDto = accountService.update(id, accountDto);
        return ResponseEntity.ok(updatedAccountDto);
    }
}
