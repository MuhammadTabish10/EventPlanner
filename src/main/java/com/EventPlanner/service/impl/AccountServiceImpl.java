package com.EventPlanner.service.impl;

import com.EventPlanner.dto.AccountDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.repository.*;
import com.EventPlanner.service.AccountService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final IndustryRepository industryRepository;
    private final CurrencyRepository currencyRepository;
    private final LocationRepository locationRepository;
    private final TagRepository tagRepository;

    public AccountServiceImpl(AccountRepository accountRepository, IndustryRepository industryRepository, CurrencyRepository currencyRepository, LocationRepository locationRepository, TagRepository tagRepository) {
        this.accountRepository = accountRepository;
        this.industryRepository = industryRepository;
        this.currencyRepository = currencyRepository;
        this.locationRepository = locationRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public AccountDto save(AccountDto accountDto) {
        Account account = toEntity(accountDto);
        account.setStatus(true);
        account.setIndustry(industryRepository.findById(account.getIndustry().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Industry not found for id => %d", account.getIndustry().getId()))));
        account.setCurrency(currencyRepository.findById(account.getCurrency().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Currency not found for id => %d", account.getCurrency().getId()))));
        account.setLocation(locationRepository.findById(account.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", account.getLocation().getId()))));
        account.setTag(tagRepository.findById(account.getTag().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Tag not found for id => %d", account.getTag().getId()))));
        Account createdAccount =  accountRepository.save(account);
        return toDto(createdAccount);
    }

    @Override
    public List<AccountDto> getAll() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountDtoList = new ArrayList<>();

        for (Account account : accounts) {
            AccountDto accountDto = toDto(account);
            accountDtoList.add(accountDto);
        }
        return accountDtoList;
    }

    @Override
    public AccountDto findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", id)));
        return toDto(account);
    }

    @Override
    public AccountDto findByName(String name) {
        Account account = accountRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for name => %s", name)));
        return toDto(account);
    }

    @Override
    public List<AccountDto> searchByName(String name) {
        List<Account> accounts = accountRepository.findAccountsByName(name);
        List<AccountDto> accountDtoList = new ArrayList<>();

        for (Account account : accounts) {
            AccountDto accountDto = toDto(account);
            accountDtoList.add(accountDto);
        }
        return accountDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", id)));
        accountRepository.setStatusInactive(account.getId());
    }

    @Override
    @Transactional
    public AccountDto update(Long id, AccountDto accountDto) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", id)));

        existingAccount.setName(accountDto.getName());
        existingAccount.setPhone(accountDto.getPhone());
        existingAccount.setWebsite(accountDto.getWebsite());
        existingAccount.setStatus(accountDto.getStatus());

        existingAccount.setIndustry(industryRepository.findById(accountDto.getIndustry().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Industry not found for id => %d", accountDto.getIndustry().getId()))));
        existingAccount.setCurrency(currencyRepository.findById(accountDto.getCurrency().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Currency not found for id => %d", accountDto.getCurrency().getId()))));
        existingAccount.setLocation(locationRepository.findById(accountDto.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", accountDto.getLocation().getId()))));
        existingAccount.setTag(tagRepository.findById(accountDto.getTag().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Tag not found for id => %d", accountDto.getTag().getId()))));
        Account updatedAccount = accountRepository.save(existingAccount);
        return toDto(updatedAccount);
    }

    public AccountDto toDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .createdAt(account.getCreatedAt())
                .name(account.getName())
                .phone(account.getPhone())
                .website(account.getWebsite())
                .status(account.getStatus())
                .industry(account.getIndustry())
                .currency(account.getCurrency())
                .location(account.getLocation())
                .tag(account.getTag())
                .build();
    }

    public Account toEntity(AccountDto accountDto) {
        return Account.builder()
                .id(accountDto.getId())
                .createdAt(accountDto.getCreatedAt())
                .name(accountDto.getName())
                .phone(accountDto.getPhone())
                .website(accountDto.getWebsite())
                .status(accountDto.getStatus())
                .industry(accountDto.getIndustry())
                .currency(accountDto.getCurrency())
                .location(accountDto.getLocation())
                .tag(accountDto.getTag())
                .build();
    }
}
