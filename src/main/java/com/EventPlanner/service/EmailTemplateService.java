package com.EventPlanner.service;

import com.EventPlanner.dto.EmailTemplateDto;
import com.EventPlanner.dto.PaginationResponse;

import java.util.List;

public interface EmailTemplateService {
    EmailTemplateDto save(EmailTemplateDto emailTemplateDto);
    List<EmailTemplateDto> getAll();
    PaginationResponse getAllPaginatedEmailTemplate(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    EmailTemplateDto findById(Long id);
    EmailTemplateDto findByName(String name);
    void deleteById(Long id);
    EmailTemplateDto update(Long id, EmailTemplateDto emailTemplateDto);
}
