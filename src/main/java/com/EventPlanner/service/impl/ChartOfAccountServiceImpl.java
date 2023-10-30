package com.EventPlanner.service.impl;

import com.EventPlanner.dto.ChartOfAccountDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.ChartOfAccount;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.ChartOfAccountRepository;
import com.EventPlanner.service.ChartOfAccountService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartOfAccountServiceImpl implements ChartOfAccountService {
    private final ChartOfAccountRepository chartOfAccountRepository;
    private final AccountRepository accountRepository;

    public ChartOfAccountServiceImpl(ChartOfAccountRepository chartOfAccountRepository, AccountRepository accountRepository) {
        this.chartOfAccountRepository = chartOfAccountRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public ChartOfAccountDto save(ChartOfAccountDto chartOfAccountDto) {
        ChartOfAccount chartOfAccount = toEntity(chartOfAccountDto);
        chartOfAccount.setStatus(true);

        Account account = accountRepository.findById(chartOfAccount.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", chartOfAccount.getAccount().getId())));

        chartOfAccount.setAccount(account);
        ChartOfAccount createdChartOfAccount = chartOfAccountRepository.save(chartOfAccount);
        return toDto(createdChartOfAccount);
    }

    @Override
    public List<ChartOfAccountDto> getAll() {
        List<ChartOfAccount> chartOfAccountList = chartOfAccountRepository.findAllInDesOrderByIdAndStatus();
        List<ChartOfAccountDto> chartOfAccountDtoList = new ArrayList<>();

        for (ChartOfAccount chartOfAccount : chartOfAccountList) {
            ChartOfAccountDto chartOfAccountDto = toDto(chartOfAccount);
            chartOfAccountDtoList.add(chartOfAccountDto);
        }
        return chartOfAccountDtoList;
    }

    @Override
    public ChartOfAccountDto findById(Long id) {
        ChartOfAccount chartOfAccount = chartOfAccountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Chart Of Account not found for id => %d", id)));
        return toDto(chartOfAccount);
    }

    @Override
    public ChartOfAccountDto findByName(String name) {
        ChartOfAccount chartOfAccount = chartOfAccountRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Chart Of Account not found for name => %s", name)));
        return toDto(chartOfAccount);
    }

    @Override
    public List<ChartOfAccountDto> searchByName(String name) {
        List<ChartOfAccount> chartOfAccountList = chartOfAccountRepository.findChartOfAccountByName(name);
        List<ChartOfAccountDto> chartOfAccountDtoList = new ArrayList<>();

        for (ChartOfAccount chartOfAccount : chartOfAccountList) {
            ChartOfAccountDto chartOfAccountDto = toDto(chartOfAccount);
            chartOfAccountDtoList.add(chartOfAccountDto);
        }
        return chartOfAccountDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ChartOfAccount chartOfAccount = chartOfAccountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Chart Of Account not found for id => %d", id)));
        chartOfAccountRepository.setStatusInactive(chartOfAccount.getId());
    }

    @Override
    @Transactional
    public ChartOfAccountDto update(Long id, ChartOfAccountDto chartOfAccountDto) {
        ChartOfAccount existingChartOfAccount = chartOfAccountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Chart Of Account not found for id => %d", id)));

        existingChartOfAccount.setName(chartOfAccountDto.getName());
        existingChartOfAccount.setGlAccount(chartOfAccountDto.getGlAccount());
        existingChartOfAccount.setDescription(chartOfAccountDto.getDescription());
        existingChartOfAccount.setType(chartOfAccountDto.getType());
        existingChartOfAccount.setStatus(chartOfAccountDto.getStatus());

        existingChartOfAccount.setAccount(accountRepository.findById(chartOfAccountDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", chartOfAccountDto.getAccount().getId()))));

        ChartOfAccount updatedChartOfAccount = chartOfAccountRepository.save(existingChartOfAccount);
        return toDto(updatedChartOfAccount);
    }

    public ChartOfAccountDto toDto(ChartOfAccount chartOfAccount) {
        return ChartOfAccountDto.builder()
                .id(chartOfAccount.getId())
                .name(chartOfAccount.getName())
                .glAccount(chartOfAccount.getGlAccount())
                .description(chartOfAccount.getDescription())
                .type(chartOfAccount.getType())
                .status(chartOfAccount.getStatus())
                .account(chartOfAccount.getAccount())
                .build();
    }

    public ChartOfAccount toEntity(ChartOfAccountDto chartOfAccountDto) {
        return ChartOfAccount.builder()
                .id(chartOfAccountDto.getId())
                .name(chartOfAccountDto.getName())
                .glAccount(chartOfAccountDto.getGlAccount())
                .description(chartOfAccountDto.getDescription())
                .type(chartOfAccountDto.getType())
                .status(chartOfAccountDto.getStatus())
                .account(chartOfAccountDto.getAccount())
                .build();
    }
}
