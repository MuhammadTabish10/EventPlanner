package com.EventPlanner.service.impl;

import com.EventPlanner.dto.ContactTypeDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.ContactType;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.ContactTypeRepository;
import com.EventPlanner.service.ContactTypeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactTypeServiceImpl implements ContactTypeService {
    private final ContactTypeRepository contactTypeRepository;
    private final AccountRepository accountRepository;

    public ContactTypeServiceImpl(ContactTypeRepository contactTypeRepository, AccountRepository accountRepository) {
        this.contactTypeRepository = contactTypeRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public ContactTypeDto save(ContactTypeDto contactTypeDto) {
        ContactType contactType = toEntity(contactTypeDto);
        contactType.setStatus(true);

        Account account = accountRepository.findById(contactType.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", contactType.getAccount().getId())));

        contactType.setAccount(account);
        ContactType createdContactType = contactTypeRepository.save(contactType);
        return toDto(createdContactType);
    }

    @Override
    public List<ContactTypeDto> getAll() {
        List<ContactType> contactTypeList = contactTypeRepository.findAllInDesOrderByIdAndStatus();
        List<ContactTypeDto> contactTypeDtoList = new ArrayList<>();

        for (ContactType contactType : contactTypeList) {
            ContactTypeDto contactTypeDto = toDto(contactType);
            contactTypeDtoList.add(contactTypeDto);
        }
        return contactTypeDtoList;
    }

    @Override
    public ContactTypeDto findById(Long id) {
        ContactType contactType = contactTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("ContactType not found for id => %d", id)));
        return toDto(contactType);
    }

    @Override
    public ContactTypeDto findByType(String type) {
        ContactType contactType = contactTypeRepository.findByType(type)
                .orElseThrow(() -> new RecordNotFoundException(String.format("ContactType not found for name => %s", type)));
        return toDto(contactType);
    }

    @Override
    public List<ContactTypeDto> searchByType(String type) {
        List<ContactType> contactTypeList = contactTypeRepository.findContactTypeByType(type);
        List<ContactTypeDto> contactTypeDtoList = new ArrayList<>();

        for (ContactType contactType : contactTypeList) {
            ContactTypeDto contactTypeDto = toDto(contactType);
            contactTypeDtoList.add(contactTypeDto);
        }
        return contactTypeDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ContactType contactType = contactTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("ContactType not found for id => %d", id)));
        contactTypeRepository.setStatusInactive(contactType.getId());
    }

    @Override
    @Transactional
    public ContactTypeDto update(Long id, ContactTypeDto contactTypeDto) {
        ContactType existingContactType = contactTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("ContactType not found for id => %d", id)));

        existingContactType.setType(contactTypeDto.getType());
        existingContactType.setStatus(contactTypeDto.getStatus());

        existingContactType.setAccount(accountRepository.findById(contactTypeDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", contactTypeDto.getAccount().getId()))));

        ContactType updatedContactType = contactTypeRepository.save(existingContactType);
        return toDto(updatedContactType);
    }

    public ContactTypeDto toDto(ContactType contactType) {
        return ContactTypeDto.builder()
                .id(contactType.getId())
                .type(contactType.getType())
                .status(contactType.getStatus())
                .account(contactType.getAccount())
                .build();
    }

    public ContactType toEntity(ContactTypeDto contactTypeDto) {
        return ContactType.builder()
                .id(contactTypeDto.getId())
                .type(contactTypeDto.getType())
                .status(contactTypeDto.getStatus())
                .account(contactTypeDto.getAccount())
                .build();
    }
}
