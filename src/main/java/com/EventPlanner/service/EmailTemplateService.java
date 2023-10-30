package com.EventPlanner.service;

import com.EventPlanner.dto.EmailTemplateDto;

import java.util.List;

public interface EmailTemplateService {
    EmailTemplateDto save(EmailTemplateDto emailTemplateDto);
    List<EmailTemplateDto> getAll();
    EmailTemplateDto findById(Long id);
    EmailTemplateDto findByName(String name);
    List<EmailTemplateDto> searchByName(String name);
    void deleteById(Long id);
    EmailTemplateDto update(Long id, EmailTemplateDto emailTemplateDto);
}
