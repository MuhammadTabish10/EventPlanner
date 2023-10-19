package com.EventPlanner.service.impl;

import com.EventPlanner.dto.SubAccountDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.Location;
import com.EventPlanner.model.SubAccount;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.LocationRepository;
import com.EventPlanner.repository.SubAccountRepository;
import com.EventPlanner.service.SubAccountService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubAccountServiceImpl implements SubAccountService {

    private final SubAccountRepository subAccountRepository;
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;

    public SubAccountServiceImpl(SubAccountRepository subAccountRepository, AccountRepository accountRepository, LocationRepository locationRepository) {
        this.subAccountRepository = subAccountRepository;
        this.accountRepository = accountRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public SubAccountDto save(SubAccountDto subAccountDto) {
        SubAccount subAccount = toEntity(subAccountDto);
        subAccount.setStatus(true);
        if(subAccount.getCreatedAt() == null){
            subAccount.setCreatedAt(LocalDateTime.now());
        }
        Account account = accountRepository.findById(subAccount.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", subAccount.getAccount().getId())));

        Location location = locationRepository.findById(subAccount.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", subAccount.getLocation().getId())));

        subAccount.setAccount(account);
        subAccount.setLocation(location);
        SubAccount savedSubAccount = subAccountRepository.save(subAccount);
        return toDto(savedSubAccount);
    }

    @Override
    public List<SubAccountDto> getAll() {
        List<SubAccount> subAccountList = subAccountRepository.findAllInDesOrderByIdAndStatus();
        List<SubAccountDto> subAccountDtoList = new ArrayList<>();

        for (SubAccount subAccount : subAccountList) {
            SubAccountDto subAccountDto = toDto(subAccount);
            subAccountDtoList.add(subAccountDto);
        }
        return subAccountDtoList;
    }

    @Override
    public SubAccountDto findById(Long id) {
        SubAccount subAccount = subAccountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for Id => %d", id)));
        return toDto(subAccount);
    }

    @Override
    public SubAccountDto findByName(String name) {
        SubAccount subAccount = subAccountRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for name => %s", name)));
        return toDto(subAccount);
    }

    @Override
    public List<SubAccountDto> searchByName(String name) {
        List<SubAccount> subAccountList = subAccountRepository.findSubAccountByName(name);
        List<SubAccountDto> subAccountDtoList = new ArrayList<>();

        for (SubAccount subAccount : subAccountList) {
            SubAccountDto subAccountDto = toDto(subAccount);
            subAccountDtoList.add(subAccountDto);
        }
        return subAccountDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        SubAccount subAccount = subAccountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for Id => %d", id)));
        subAccountRepository.setStatusInactive(id);
    }

    @Override
    @Transactional
    public SubAccountDto update(Long id, SubAccountDto subAccountDto) {
        SubAccount existingSubAccount = subAccountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for Id => %d", id)));

        existingSubAccount.setLocation(subAccountDto.getLocation());
        existingSubAccount.setPhone(subAccountDto.getPhone());
        existingSubAccount.setWebsite(subAccountDto.getWebsite());
        existingSubAccount.setStatus(subAccountDto.getStatus());

        existingSubAccount.setAccount(accountRepository.findById(subAccountDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", subAccountDto.getAccount().getId()))));

        existingSubAccount.setLocation(locationRepository.findById(subAccountDto.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", subAccountDto.getLocation().getId()))));

        SubAccount updatedSubAccount = subAccountRepository.save(existingSubAccount);
        return toDto(updatedSubAccount);
    }

    public SubAccountDto toDto(SubAccount subAccount) {
        return SubAccountDto.builder()
                .id(subAccount.getId())
                .createdAt(subAccount.getCreatedAt())
                .phone(subAccount.getPhone())
                .website(subAccount.getWebsite())
                .status(subAccount.getStatus())
                .account(subAccount.getAccount())
                .location(subAccount.getLocation())
                .build();
    }

    public SubAccount toEntity(SubAccountDto subAccountDto) {
        return SubAccount.builder()
                .id(subAccountDto.getId())
                .createdAt(subAccountDto.getCreatedAt())
                .phone(subAccountDto.getPhone())
                .website(subAccountDto.getWebsite())
                .status(subAccountDto.getStatus())
                .account(subAccountDto.getAccount())
                .location(subAccountDto.getLocation())
                .build();
    }
}
