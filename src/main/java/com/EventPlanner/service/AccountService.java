package com.EventPlanner.service;

import com.EventPlanner.dto.AccountDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface AccountService {
    AccountDto save(AccountDto accountDto);
    List<AccountDto> getAll();
    PaginationResponse getAllPaginatedAccounts(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);

    AccountDto findById(Long id);
    AccountDto findByName(String name);
    void deleteById(Long id);
    AccountDto update(Long id, AccountDto accountDto);
}
