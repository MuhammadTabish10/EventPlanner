package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.UserDto;
import com.EventPlanner.dto.VenueDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.*;
import com.EventPlanner.repository.*;
import com.EventPlanner.service.VenueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;
    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;
    private final EventTypeRepository eventTypeRepository;

    public VenueServiceImpl(VenueRepository venueRepository, ContactRepository contactRepository, AccountRepository accountRepository, LocationRepository locationRepository, EventTypeRepository eventTypeRepository) {
        this.venueRepository = venueRepository;
        this.contactRepository = contactRepository;
        this.accountRepository = accountRepository;
        this.locationRepository = locationRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    @Transactional
    public VenueDto save(VenueDto venueDto) {
        Venue venue = toEntity(venueDto);
        venue.setStatus(true);

        Account account = accountRepository.findById(venue.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", venue.getAccount().getId())));

        Location location = locationRepository.findById(venue.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", venue.getLocation().getId())));

        EventType eventType = eventTypeRepository.findById(venue.getEventType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event type not found for id => %d", venue.getEventType().getId())));

        Set<Contact> contactList = new HashSet<>();
        for(Contact contact : venue.getContacts()){
            Contact savedContact = contactRepository.findById(contact.getId())
                    .orElseThrow(()-> new RecordNotFoundException(String.format("Contact not found for id => %d", contact.getId())));
            contactList.add(savedContact);
        }

        venue.setAccount(account);
        venue.setLocation(location);
        venue.setEventType(eventType);
        venue.setContacts(contactList);
        Venue createdVenue = venueRepository.save(venue);
        return toDto(createdVenue);
    }

    @Override
    public List<VenueDto> getAll() {
        List<Venue> venueList = venueRepository.findAllInDesOrderByIdAndStatus();
        List<VenueDto> venueDtoList = new ArrayList<>();

        for (Venue venue : venueList) {
            VenueDto venueDto = toDto(venue);
            venueDtoList.add(venueDto);
        }
        return venueDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedVenue(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Venue> pageVenue = venueRepository.findAllInDesOrderByIdAndStatus(page);
        List<Venue> venueList = pageVenue.getContent();

        List<VenueDto> venueDtoList = new ArrayList<>();
        for (Venue venue : venueList) {
            VenueDto venueDto = toDto(venue);
            venueDtoList.add(venueDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(venueDtoList);
        paginationResponse.setPageNumber(pageVenue.getNumber());
        paginationResponse.setPageSize(pageVenue.getSize());
        paginationResponse.setTotalElements(pageVenue.getNumberOfElements());
        paginationResponse.setTotalPages(pageVenue.getTotalPages());
        paginationResponse.setLastPage(pageVenue.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Venue> pageVenue = venueRepository.findVenueByName(name,page);
        List<Venue> venueList = pageVenue.getContent();

        List<VenueDto> venueDtoList = new ArrayList<>();
        for (Venue venue : venueList) {
            VenueDto venueDto = toDto(venue);
            venueDtoList.add(venueDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(venueDtoList);
        paginationResponse.setPageNumber(pageVenue.getNumber());
        paginationResponse.setPageSize(pageVenue.getSize());
        paginationResponse.setTotalElements(pageVenue.getNumberOfElements());
        paginationResponse.setTotalPages(pageVenue.getTotalPages());
        paginationResponse.setLastPage(pageVenue.isLast());

        return paginationResponse;
    }

    @Override
    public VenueDto findById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", id)));
        return toDto(venue);
    }

    @Override
    public VenueDto findByName(String name) {
        Venue venue = venueRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for name => %s", name)));
        return toDto(venue);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", id)));
        venueRepository.setStatusInactive(venue.getId());
    }

    @Override
    @Transactional
    public VenueDto update(Long id, VenueDto venueDto) {
        Venue existingVenue = venueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", id)));

        existingVenue.setName(venueDto.getName());
        existingVenue.setPhone(venueDto.getPhone());
        existingVenue.setCapacity(venueDto.getCapacity());
        existingVenue.setAttachments(venueDto.getAttachments());
        existingVenue.setGoogleMapLink(venueDto.getGoogleMapLink());
        existingVenue.setPrimaryContactEmail(venueDto.getPrimaryContactEmail());
        existingVenue.setPrimaryContactName(venueDto.getPrimaryContactName());
        existingVenue.setPrimaryContactPhone(venueDto.getPrimaryContactPhone());
        existingVenue.setWebsite(venueDto.getWebsite());
        existingVenue.setTwitter(venueDto.getTwitter());
        existingVenue.setInstagram(venueDto.getInstagram());
        existingVenue.setLinkedin(venueDto.getLinkedin());

        existingVenue.setAccount(accountRepository.findById(venueDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", venueDto.getAccount().getId()))));

        existingVenue.setLocation(locationRepository.findById(venueDto.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", venueDto.getLocation().getId()))));

        existingVenue.setEventType(eventTypeRepository.findById(venueDto.getEventType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event type not found for id => %d", venueDto.getEventType().getId()))));

        existingVenue.getContacts().removeIf(contact -> !venueDto.getContacts().contains(contact));

        Set<Contact> contacts = venueDto.getContacts().stream()
                .map(contact -> contactRepository.findById(contact.getId())
                        .orElseThrow(() -> new RecordNotFoundException(String.format("Contact not found for id => %d", contact.getId()))))
                .collect(Collectors.toSet());

        existingVenue.setContacts(contacts);
        Venue updatedVenue = venueRepository.save(existingVenue);
        return toDto(updatedVenue);
    }

    public VenueDto toDto(Venue venue) {
        return VenueDto.builder()
                .id(venue.getId())
                .createdAt(venue.getCreatedAt())
                .name(venue.getName())
                .phone(venue.getPhone())
                .website(venue.getWebsite())
                .capacity(venue.getCapacity())
                .twitter(venue.getTwitter())
                .instagram(venue.getInstagram())
                .linkedin(venue.getLinkedin())
                .attachments(venue.getAttachments())
                .googleMapLink(venue.getGoogleMapLink())
                .primaryContactName(venue.getPrimaryContactName())
                .primaryContactPhone(venue.getPrimaryContactPhone())
                .primaryContactEmail(venue.getPrimaryContactEmail())
                .status(venue.getStatus())
                .location(venue.getLocation())
                .eventType(venue.getEventType())
                .account(venue.getAccount())
                .contacts(venue.getContacts())
                .build();
    }

    public Venue toEntity(VenueDto venueDto) {
        return Venue.builder()
                .id(venueDto.getId())
                .createdAt(venueDto.getCreatedAt())
                .name(venueDto.getName())
                .phone(venueDto.getPhone())
                .website(venueDto.getWebsite())
                .capacity(venueDto.getCapacity())
                .twitter(venueDto.getTwitter())
                .instagram(venueDto.getInstagram())
                .linkedin(venueDto.getLinkedin())
                .attachments(venueDto.getAttachments())
                .googleMapLink(venueDto.getGoogleMapLink())
                .primaryContactName(venueDto.getPrimaryContactName())
                .primaryContactPhone(venueDto.getPrimaryContactPhone())
                .primaryContactEmail(venueDto.getPrimaryContactEmail())
                .status(venueDto.getStatus())
                .location(venueDto.getLocation())
                .eventType(venueDto.getEventType())
                .account(venueDto.getAccount())
                .contacts(venueDto.getContacts())
                .build();
    }
}
