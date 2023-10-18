package com.EventPlanner.service;

import com.EventPlanner.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto save(AccountDto accountDto);
    List<AccountDto> getAll();
    AccountDto findById(Long id);
    AccountDto findByName(String name);
    List<AccountDto> searchByName(String name);
    void deleteById(Long id);
    AccountDto update(Long id, AccountDto accountDto);
}
