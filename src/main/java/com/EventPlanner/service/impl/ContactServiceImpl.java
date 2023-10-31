package com.EventPlanner.service.impl;

import com.EventPlanner.dto.ContactDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.*;
import com.EventPlanner.repository.*;
import com.EventPlanner.service.ContactService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;
    private final SubAccountRepository subAccountRepository;
    private final LocationRepository locationRepository;
    private final ContactTypeRepository contactTypeRepository;

    public ContactServiceImpl(ContactRepository contactRepository, AccountRepository accountRepository, SubAccountRepository subAccountRepository, LocationRepository locationRepository, ContactTypeRepository contactTypeRepository) {
        this.contactRepository = contactRepository;
        this.accountRepository = accountRepository;
        this.subAccountRepository = subAccountRepository;
        this.locationRepository = locationRepository;
        this.contactTypeRepository = contactTypeRepository;
    }

    @Override
    @Transactional
    public ContactDto save(ContactDto contactDto) {
        Contact contact = toEntity(contactDto);
        contact.setStatus(true);

        Account account = accountRepository.findById(contact.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", contact.getAccount().getId())));

        SubAccount subAccount = subAccountRepository.findById(contact.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sub Account not found for id => %d", contact.getSubAccount().getId())));

        Location location = locationRepository.findById(contact.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", contact.getLocation().getId())));

        ContactType contactType = contactTypeRepository.findById(contact.getContactType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Contact type not found for id => %d", contact.getContactType().getId())));

        contact.setAccount(account);
        contact.setSubAccount(subAccount);
        contact.setLocation(location);
        contact.setContactType(contactType);
        Contact createdContact = contactRepository.save(contact);
        return toDto(createdContact);
    }

    @Override
    public List<ContactDto> getAll() {
        List<Contact> contactList = contactRepository.findAllInDesOrderByIdAndStatus();
        List<ContactDto> contactDtoList = new ArrayList<>();

        for (Contact contact : contactList) {
            ContactDto contactDto = toDto(contact);
            contactDtoList.add(contactDto);
        }
        return contactDtoList;
    }

    @Override
    public ContactDto findById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Contact not found for id => %d", id)));
        return toDto(contact);
    }

    @Override
    public ContactDto findByCustomer(String customer) {
        Contact contact = contactRepository.findByCustomer(customer)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Customer not found for name => %s", customer)));
        return toDto(contact);
    }

    @Override
    public List<ContactDto> searchByCustomer(String customer) {
        List<Contact> contactList = contactRepository.findContactByCustomer(customer);
        List<ContactDto> contactDtoList = new ArrayList<>();

        for (Contact contact : contactList) {
            ContactDto contactDto = toDto(contact);
            contactDtoList.add(contactDto);
        }
        return contactDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Contact not found for id => %d", id)));
        contactRepository.setStatusInactive(contact.getId());
    }

    @Override
    @Transactional
    public ContactDto update(Long id, ContactDto contactDto) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Contact not found for id => %d", id)));

        existingContact.setCustomer(contactDto.getCustomer());
        existingContact.setFirstName(contactDto.getFirstName());
        existingContact.setLastName(contactDto.getLastName());
        existingContact.setJobTitle(contactDto.getJobTitle());
        existingContact.setEmail(contactDto.getEmail());
        existingContact.setPhone(contactDto.getPhone());
        existingContact.setProfileDescription(contactDto.getProfileDescription());
        existingContact.setBioDescription(contactDto.getBioDescription());
        existingContact.setHeadShotImage(contactDto.getHeadShotImage());
        existingContact.setWebsite(contactDto.getWebsite());
        existingContact.setTwitter(contactDto.getTwitter());
        existingContact.setInstagram(contactDto.getInstagram());
        existingContact.setLinkedin(contactDto.getLinkedin());

        existingContact.setAccount(accountRepository.findById(contactDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", contactDto.getAccount().getId()))));

        existingContact.setSubAccount(subAccountRepository.findById(contactDto.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sub Account not found for id => %d", contactDto.getSubAccount().getId()))));

        existingContact.setLocation(locationRepository.findById(contactDto.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", contactDto.getLocation().getId()))));

        existingContact.setContactType(contactTypeRepository.findById(contactDto.getContactType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Contact type not found for id => %d", contactDto.getContactType().getId()))));

        Contact updatedContact = contactRepository.save(existingContact);
        return toDto(updatedContact);
    }

    public ContactDto toDto(Contact contact) {
        return ContactDto.builder()
                .id(contact.getId())
                .createdAt(contact.getCreatedAt())
                .customer(contact.getCustomer())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .jobTitle(contact.getJobTitle())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .profileDescription(contact.getProfileDescription())
                .bioDescription(contact.getBioDescription())
                .headShotImage(contact.getHeadShotImage())
                .website(contact.getWebsite())
                .twitter(contact.getTwitter())
                .instagram(contact.getInstagram())
                .linkedin(contact.getLinkedin())
                .status(contact.getStatus())
                .account(contact.getAccount())
                .subAccount(contact.getSubAccount())
                .location(contact.getLocation())
                .contactType(contact.getContactType())
                .build();
    }

    public Contact toEntity(ContactDto contactDto) {
        return Contact.builder()
                .id(contactDto.getId())
                .createdAt(contactDto.getCreatedAt())
                .customer(contactDto.getCustomer())
                .firstName(contactDto.getFirstName())
                .lastName(contactDto.getLastName())
                .jobTitle(contactDto.getJobTitle())
                .email(contactDto.getEmail())
                .phone(contactDto.getPhone())
                .profileDescription(contactDto.getProfileDescription())
                .bioDescription(contactDto.getBioDescription())
                .headShotImage(contactDto.getHeadShotImage())
                .website(contactDto.getWebsite())
                .twitter(contactDto.getTwitter())
                .instagram(contactDto.getInstagram())
                .linkedin(contactDto.getLinkedin())
                .status(contactDto.getStatus())
                .account(contactDto.getAccount())
                .subAccount(contactDto.getSubAccount())
                .location(contactDto.getLocation())
                .contactType(contactDto.getContactType())
                .build();
    }
}
