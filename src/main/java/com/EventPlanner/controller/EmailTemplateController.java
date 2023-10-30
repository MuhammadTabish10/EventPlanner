package com.EventPlanner.controller;

import com.EventPlanner.dto.EmailTemplateDto;
import com.EventPlanner.service.EmailTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmailTemplateController {
    private final EmailTemplateService emailTemplateService;

    public EmailTemplateController(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    @PostMapping("/email-template")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmailTemplateDto> createEmailTemplate(@Valid @RequestBody EmailTemplateDto emailTemplateDto) {
        return ResponseEntity.ok(emailTemplateService.save(emailTemplateDto));
    }

    @GetMapping("/email-template")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmailTemplateDto>> getAllEmailTemplate() {
        List<EmailTemplateDto> emailTemplateDtoList = emailTemplateService.getAll();
        return ResponseEntity.ok(emailTemplateDtoList);
    }

    @GetMapping("/email-template/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmailTemplateDto> getEmailTemplateById(@PathVariable Long id) {
        EmailTemplateDto emailTemplateDto = emailTemplateService.findById(id);
        return ResponseEntity.ok(emailTemplateDto);
    }

    @GetMapping("/email-template/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmailTemplateDto> getEmailTemplateByName(@PathVariable String name) {
        EmailTemplateDto emailTemplateDto = emailTemplateService.findByName(name);
        return ResponseEntity.ok(emailTemplateDto);
    }

    @GetMapping("/email-template/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmailTemplateDto>> getAllEmailTemplatesByName(@PathVariable String name) {
        List<EmailTemplateDto> emailTemplateDtoList = emailTemplateService.searchByName(name);
        return ResponseEntity.ok(emailTemplateDtoList);
    }

    @DeleteMapping("/email-template/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEmailTemplate(@PathVariable Long id) {
        emailTemplateService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email-template/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmailTemplateDto> updateEmailTemplate(@PathVariable Long id, @Valid @RequestBody EmailTemplateDto emailTemplateDto) {
        EmailTemplateDto updatedEmailTemplateDto = emailTemplateService.update(id, emailTemplateDto);
        return ResponseEntity.ok(updatedEmailTemplateDto);
    }
}
