package com.EventPlanner.service.impl;

import com.EventPlanner.dto.ContactTypeDto;
import com.EventPlanner.dto.EmailTemplateDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.ContactType;
import com.EventPlanner.model.EmailTemplate;
import com.EventPlanner.model.SubAccount;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.EmailTemplateRepository;
import com.EventPlanner.repository.SubAccountRepository;
import com.EventPlanner.service.EmailTemplateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {
    private final EmailTemplateRepository emailTemplateRepository;
    private final AccountRepository accountRepository;
    private final SubAccountRepository subAccountRepository;

    public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository, AccountRepository accountRepository, SubAccountRepository subAccountRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
        this.accountRepository = accountRepository;
        this.subAccountRepository = subAccountRepository;
    }


    @Override
    @Transactional
    public EmailTemplateDto save(EmailTemplateDto emailTemplateDto) {
        EmailTemplate emailTemplate = toEntity(emailTemplateDto);
        emailTemplate.setStatus(true);

        Account account = accountRepository.findById(emailTemplate.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", emailTemplate.getAccount().getId())));

        SubAccount subAccount = subAccountRepository.findById(emailTemplate.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for id => %d", emailTemplate.getSubAccount().getId())));

        emailTemplate.setAccount(account);
        emailTemplate.setSubAccount(subAccount);

        EmailTemplate createdEmailTemplate = emailTemplateRepository.save(emailTemplate);
        return toDto(createdEmailTemplate);
    }

    @Override
    public List<EmailTemplateDto> getAll() {
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAllInDesOrderByIdAndStatus();
        List<EmailTemplateDto> emailTemplateDtoList = new ArrayList<>();

        for (EmailTemplate emailTemplate : emailTemplateList) {
            EmailTemplateDto emailTemplateDto = toDto(emailTemplate);
            emailTemplateDtoList.add(emailTemplateDto);
        }
        return emailTemplateDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedEmailTemplate(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<EmailTemplate> pageEmailTemplate = emailTemplateRepository.findAllInDesOrderByIdAndStatus(page);
        List<EmailTemplate> emailTemplateList = pageEmailTemplate.getContent();

        List<EmailTemplateDto> emailTemplateDtoList = new ArrayList<>();
        for (EmailTemplate emailTemplate : emailTemplateList) {
            EmailTemplateDto emailTemplateDto = toDto(emailTemplate);
            emailTemplateDtoList.add(emailTemplateDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(emailTemplateDtoList);
        paginationResponse.setPageNumber(pageEmailTemplate.getNumber());
        paginationResponse.setPageSize(pageEmailTemplate.getSize());
        paginationResponse.setTotalElements(pageEmailTemplate.getNumberOfElements());
        paginationResponse.setTotalPages(pageEmailTemplate.getTotalPages());
        paginationResponse.setLastPage(pageEmailTemplate.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<EmailTemplate> pageEmailTemplate = emailTemplateRepository.findEmailTemplateByName(name,page);
        List<EmailTemplate> emailTemplateList = pageEmailTemplate.getContent();

        List<EmailTemplateDto> emailTemplateDtoList = new ArrayList<>();
        for (EmailTemplate emailTemplate : emailTemplateList) {
            EmailTemplateDto emailTemplateDto = toDto(emailTemplate);
            emailTemplateDtoList.add(emailTemplateDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(emailTemplateDtoList);
        paginationResponse.setPageNumber(pageEmailTemplate.getNumber());
        paginationResponse.setPageSize(pageEmailTemplate.getSize());
        paginationResponse.setTotalElements(pageEmailTemplate.getNumberOfElements());
        paginationResponse.setTotalPages(pageEmailTemplate.getTotalPages());
        paginationResponse.setLastPage(pageEmailTemplate.isLast());

        return paginationResponse;
    }

    @Override
    public EmailTemplateDto findById(Long id) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Email Template not found for id => %d", id)));
        return toDto(emailTemplate);
    }

    @Override
    public EmailTemplateDto findByName(String name) {
        EmailTemplate emailTemplate = emailTemplateRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Email Template not found for name => %s", name)));
        return toDto(emailTemplate);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Email Template not found for id => %d", id)));
        emailTemplateRepository.setStatusInactive(emailTemplate.getId());
    }

    @Override
    @Transactional
    public EmailTemplateDto update(Long id, EmailTemplateDto emailTemplateDto) {
        EmailTemplate existingEmailTemplate = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Email Template not found for id => %d", id)));

        existingEmailTemplate.setName(emailTemplateDto.getName());

        existingEmailTemplate.setAccount(accountRepository.findById(emailTemplateDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", emailTemplateDto.getAccount().getId()))));

        existingEmailTemplate.setSubAccount(subAccountRepository.findById(emailTemplateDto.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for id => %d", emailTemplateDto.getSubAccount().getId()))));

        EmailTemplate updatedEmailTemplate = emailTemplateRepository.save(existingEmailTemplate);
        return toDto(updatedEmailTemplate);
    }

    public EmailTemplateDto toDto(EmailTemplate emailTemplate) {
        return EmailTemplateDto.builder()
                .id(emailTemplate.getId())
                .name(emailTemplate.getName())
                .status(emailTemplate.getStatus())
                .account(emailTemplate.getAccount())
                .subAccount(emailTemplate.getSubAccount())
                .build();
    }

    public EmailTemplate toEntity(EmailTemplateDto emailTemplateDto) {
        return EmailTemplate.builder()
                .id(emailTemplateDto.getId())
                .name(emailTemplateDto.getName())
                .status(emailTemplateDto.getStatus())
                .account(emailTemplateDto.getAccount())
                .subAccount(emailTemplateDto.getSubAccount())
                .build();
    }
}
