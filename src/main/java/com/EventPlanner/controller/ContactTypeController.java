package com.EventPlanner.controller;

import com.EventPlanner.dto.ContactTypeDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.ContactTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactTypeController {
    private final ContactTypeService contactTypeService;

    public ContactTypeController(ContactTypeService contactTypeService) {
        this.contactTypeService = contactTypeService;
    }

    @PostMapping("/contact-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactTypeDto> createContactType(@Valid @RequestBody ContactTypeDto contactTypeDto) {
        return ResponseEntity.ok(contactTypeService.save(contactTypeDto));
    }

    @GetMapping("/contact-type")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ContactTypeDto>> getAllContactTypes() {
        List<ContactTypeDto> contactTypeDtoList = contactTypeService.getAll();
        return ResponseEntity.ok(contactTypeDtoList);
    }

    @GetMapping("/contact-type/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedContactType(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = contactTypeService.getAllPaginatedContactType(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/contact-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactTypeDto> getContactTypeById(@PathVariable Long id) {
        ContactTypeDto contactTypeDto = contactTypeService.findById(id);
        return ResponseEntity.ok(contactTypeDto);
    }

    @GetMapping("/contact-type/type/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactTypeDto> getContactTypeByName(@PathVariable String type) {
        ContactTypeDto contactTypeDto = contactTypeService.findByType(type);
        return ResponseEntity.ok(contactTypeDto);
    }

    @GetMapping("/contact-type/types/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllContactTypesByType(
            @PathVariable String type,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = contactTypeService.searchByType(type, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/contact-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteContactType(@PathVariable Long id) {
        contactTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/contact-type/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactTypeDto> updateContactType(@PathVariable Long id, @Valid @RequestBody ContactTypeDto contactTypeDto) {
        ContactTypeDto updatedContactTypeDto = contactTypeService.update(id, contactTypeDto);
        return ResponseEntity.ok(updatedContactTypeDto);
    }
}
