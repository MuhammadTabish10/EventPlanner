package com.EventPlanner.controller;

import com.EventPlanner.dto.ContactDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contact")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactDto> createContact(@Valid @RequestBody ContactDto contactDto) {
        return ResponseEntity.ok(contactService.save(contactDto));
    }

    @GetMapping("/contact")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ContactDto>> getAllContact() {
        List<ContactDto> contactDtoList = contactService.getAll();
        return ResponseEntity.ok(contactDtoList);
    }

    @GetMapping("/contact/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedContact(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = contactService.getAllPaginatedContact(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/contact/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long id) {
        ContactDto contactDto = contactService.findById(id);
        return ResponseEntity.ok(contactDto);
    }

    @GetMapping("/contact/customer/{customer}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactDto> getContactByCustomer(@PathVariable String customer) {
        ContactDto contactDto = contactService.findByCustomer(customer);
        return ResponseEntity.ok(contactDto);
    }

    @GetMapping("/contact/customers/{customer}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllContactCustomersByCustomers(
            @PathVariable String customer,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = contactService.searchByCustomer(customer, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/contact/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/contact/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContactDto> updateContact(@PathVariable Long id, @Valid @RequestBody ContactDto contactDto) {
        ContactDto updatedContactDto = contactService.update(id, contactDto);
        return ResponseEntity.ok(updatedContactDto);
    }
}
