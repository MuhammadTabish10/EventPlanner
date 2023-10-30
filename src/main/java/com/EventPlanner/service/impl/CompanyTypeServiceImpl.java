package com.EventPlanner.service.impl;

import com.EventPlanner.dto.CompanyTypeDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.CompanyType;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.CompanyTypeRepository;
import com.EventPlanner.service.CompanyTypeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyTypeServiceImpl implements CompanyTypeService {
    private final CompanyTypeRepository companyTypeRepository;
    private final AccountRepository accountRepository;

    public CompanyTypeServiceImpl(CompanyTypeRepository companyTypeRepository, AccountRepository accountRepository) {
        this.companyTypeRepository = companyTypeRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public CompanyTypeDto save(CompanyTypeDto companyTypeDto) {
        CompanyType companyType = toEntity(companyTypeDto);
        companyType.setStatus(true);

        Account account = accountRepository.findById(companyType.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", companyType.getAccount().getId())));

        companyType.setAccount(account);
        CompanyType createdCompanyType = companyTypeRepository.save(companyType);
        return toDto(createdCompanyType);
    }

    @Override
    public List<CompanyTypeDto> getAll() {
        List<CompanyType> companyTypeList = companyTypeRepository.findAllInDesOrderByIdAndStatus();
        List<CompanyTypeDto> companyTypeDtoList = new ArrayList<>();

        for (CompanyType companyType : companyTypeList) {
            CompanyTypeDto companyTypeDto = toDto(companyType);
            companyTypeDtoList.add(companyTypeDto);
        }
        return companyTypeDtoList;
    }

    @Override
    public CompanyTypeDto findById(Long id) {
        CompanyType companyType = companyTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("CompanyType not found for id => %d", id)));
        return toDto(companyType);
    }

    @Override
    public CompanyTypeDto findByType(String type) {
        CompanyType companyType = companyTypeRepository.findByType(type)
                .orElseThrow(() -> new RecordNotFoundException(String.format("CompanyType not found for type => %s", type)));
        return toDto(companyType);
    }

    @Override
    public List<CompanyTypeDto> searchByType(String type) {
        List<CompanyType> companyTypeList = companyTypeRepository.findCompanyTypeByType(type);
        List<CompanyTypeDto> companyTypeDtoList = new ArrayList<>();

        for (CompanyType companyType : companyTypeList) {
            CompanyTypeDto companyTypeDto = toDto(companyType);
            companyTypeDtoList.add(companyTypeDto);
        }
        return companyTypeDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        CompanyType companyType = companyTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("CompanyType not found for id => %d", id)));
        companyTypeRepository.setStatusInactive(companyType.getId());
    }

    @Override
    @Transactional
    public CompanyTypeDto update(Long id, CompanyTypeDto companyTypeDto) {
        CompanyType existingCompanyType = companyTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("CompanyType not found for id => %d", id)));

        existingCompanyType.setType(companyTypeDto.getType());
        existingCompanyType.setStatus(companyTypeDto.getStatus());

        existingCompanyType.setAccount(accountRepository.findById(companyTypeDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", companyTypeDto.getAccount().getId()))));

        CompanyType updatedCompanyType = companyTypeRepository.save(existingCompanyType);
        return toDto(updatedCompanyType);
    }

    public CompanyTypeDto toDto(CompanyType companyType) {
        return CompanyTypeDto.builder()
                .id(companyType.getId())
                .type(companyType.getType())
                .status(companyType.getStatus())
                .account(companyType.getAccount())
                .build();
    }

    public CompanyType toEntity(CompanyTypeDto companyTypeDto) {
        return CompanyType.builder()
                .id(companyTypeDto.getId())
                .type(companyTypeDto.getType())
                .status(companyTypeDto.getStatus())
                .account(companyTypeDto.getAccount())
                .build();
    }
}
