package com.EventPlanner.service.impl;

import com.EventPlanner.dto.SponsorTypeDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.SponsorType;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.SponsorTypeRepository;
import com.EventPlanner.service.SponsorTypeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SponsorTypeServiceImpl implements SponsorTypeService {
    private final SponsorTypeRepository sponsorTypeRepository;
    private final AccountRepository accountRepository;

    public SponsorTypeServiceImpl(SponsorTypeRepository sponsorTypeRepository, AccountRepository accountRepository) {
        this.sponsorTypeRepository = sponsorTypeRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public SponsorTypeDto save(SponsorTypeDto sponsorTypeDto) {
        SponsorType sponsorType = toEntity(sponsorTypeDto);
        sponsorType.setStatus(true);

        Account account = accountRepository.findById(sponsorType.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", sponsorType.getAccount().getId())));

        sponsorType.setAccount(account);
        SponsorType createdSponsorType = sponsorTypeRepository.save(sponsorType);
        return toDto(createdSponsorType);
    }

    @Override
    public List<SponsorTypeDto> getAll() {
        List<SponsorType> sponsorTypeList = sponsorTypeRepository.findAllInDesOrderByIdAndStatus();
        List<SponsorTypeDto> sponsorTypeDtoList = new ArrayList<>();

        for (SponsorType sponsorType : sponsorTypeList) {
            SponsorTypeDto sponsorTypeDto = toDto(sponsorType);
            sponsorTypeDtoList.add(sponsorTypeDto);
        }
        return sponsorTypeDtoList;
    }

    @Override
    public SponsorTypeDto findById(Long id) {
        SponsorType sponsorType = sponsorTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sponsor Type not found for id => %d", id)));
        return toDto(sponsorType);
    }

    @Override
    public SponsorTypeDto findByType(String type) {
        SponsorType sponsorType = sponsorTypeRepository.findByType(type)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sponsor Type not found for type => %s", type)));
        return toDto(sponsorType);
    }

    @Override
    public List<SponsorTypeDto> searchByType(String type) {
        List<SponsorType> sponsorTypeList = sponsorTypeRepository.findSponsorTypeByType(type);
        List<SponsorTypeDto> sponsorTypeDtoList = new ArrayList<>();

        for (SponsorType sponsorType : sponsorTypeList) {
            SponsorTypeDto sponsorTypeDto = toDto(sponsorType);
            sponsorTypeDtoList.add(sponsorTypeDto);
        }
        return sponsorTypeDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        SponsorType sponsorType = sponsorTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sponsor Type not found for id => %d", id)));
        sponsorTypeRepository.setStatusInactive(sponsorType.getId());
    }

    @Override
    @Transactional
    public SponsorTypeDto update(Long id, SponsorTypeDto sponsorTypeDto) {
        SponsorType existingSponsorType = sponsorTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sponsor Type not found for id => %d", id)));

        existingSponsorType.setType(sponsorTypeDto.getType());

        existingSponsorType.setAccount(accountRepository.findById(sponsorTypeDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", sponsorTypeDto.getAccount().getId()))));

        SponsorType updatedSponsorType = sponsorTypeRepository.save(existingSponsorType);
        return toDto(updatedSponsorType);
    }

    public SponsorTypeDto toDto(SponsorType sponsorType) {
        return SponsorTypeDto.builder()
                .id(sponsorType.getId())
                .type(sponsorType.getType())
                .status(sponsorType.getStatus())
                .account(sponsorType.getAccount())
                .build();
    }

    public SponsorType toEntity(SponsorTypeDto sponsorTypeDto) {
        return SponsorType.builder()
                .id(sponsorTypeDto.getId())
                .type(sponsorTypeDto.getType())
                .status(sponsorTypeDto.getStatus())
                .account(sponsorTypeDto.getAccount())
                .build();
    }
}
