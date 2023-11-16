package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.SponsorDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.*;
import com.EventPlanner.repository.*;
import com.EventPlanner.service.SponsorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SponsorServiceImpl implements SponsorService {
    private final SponsorRepository sponsorRepository;
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;
    private final SubAccountRepository subAccountRepository;
    private final SponsorTypeRepository sponsorTypeRepository;
    private final EventRepository eventRepository;

    public SponsorServiceImpl(SponsorRepository sponsorRepository, CompanyRepository companyRepository, AccountRepository accountRepository, SubAccountRepository subAccountRepository, SponsorTypeRepository sponsorTypeRepository, EventRepository eventRepository) {
        this.sponsorRepository = sponsorRepository;
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
        this.subAccountRepository = subAccountRepository;
        this.sponsorTypeRepository = sponsorTypeRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public SponsorDto save(SponsorDto sponsorDto) {
        Sponsor sponsor = toEntity(sponsorDto);
        sponsor.setStatus(true);

        Account account = accountRepository.findById(sponsor.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", sponsor.getAccount().getId())));

        SubAccount subAccount = subAccountRepository.findById(sponsor.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for id => %d", sponsor.getSubAccount().getId())));

        Company sponsorCompany = companyRepository.findById(sponsor.getSponsorCompany().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SponsorCompany not found for id => %d", sponsor.getSponsorCompany().getId())));

        SponsorType sponsorType = sponsorTypeRepository.findById(sponsor.getSponsorType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SponsorType not found for id => %d", sponsor.getSponsorType().getId())));

        Event event = eventRepository.findById(sponsor.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", sponsor.getEvent().getId())));

        sponsor.setAccount(account);
        sponsor.setSubAccount(subAccount);
        sponsor.setSponsorCompany(sponsorCompany);
        sponsor.setSponsorType(sponsorType);
        sponsor.setEvent(event);
        Sponsor createdSponsor = sponsorRepository.save(sponsor);
        return toDto(createdSponsor);
    }

    @Override
    public List<SponsorDto> getAll() {
        List<Sponsor> sponsorList = sponsorRepository.findAllInDesOrderByIdAndStatus();
        List<SponsorDto> sponsorDtoList = new ArrayList<>();

        for (Sponsor sponsor : sponsorList) {
            SponsorDto sponsorDto = toDto(sponsor);
            sponsorDtoList.add(sponsorDto);
        }
        return sponsorDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedSponsor(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Sponsor> pageSponsor = sponsorRepository.findAllInDesOrderByIdAndStatus(page);
        List<Sponsor> sponsorList = pageSponsor.getContent();

        List<SponsorDto> sponsorDtoList = new ArrayList<>();
        for (Sponsor sponsor : sponsorList) {
            SponsorDto sponsorDto = toDto(sponsor);
            sponsorDtoList.add(sponsorDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(sponsorDtoList);
        paginationResponse.setPageNumber(pageSponsor.getNumber());
        paginationResponse.setPageSize(pageSponsor.getSize());
        paginationResponse.setTotalElements(pageSponsor.getNumberOfElements());
        paginationResponse.setTotalPages(pageSponsor.getTotalPages());
        paginationResponse.setLastPage(pageSponsor.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Sponsor> pageSponsor = sponsorRepository.findSponsorByName(name,page);
        List<Sponsor> sponsorList = pageSponsor.getContent();

        List<SponsorDto> sponsorDtoList = new ArrayList<>();
        for (Sponsor sponsor : sponsorList) {
            SponsorDto sponsorDto = toDto(sponsor);
            sponsorDtoList.add(sponsorDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(sponsorDtoList);
        paginationResponse.setPageNumber(pageSponsor.getNumber());
        paginationResponse.setPageSize(pageSponsor.getSize());
        paginationResponse.setTotalElements(pageSponsor.getNumberOfElements());
        paginationResponse.setTotalPages(pageSponsor.getTotalPages());
        paginationResponse.setLastPage(pageSponsor.isLast());

        return paginationResponse;
    }

    @Override
    public SponsorDto findById(Long id) {
        Sponsor sponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sponsor not found for id => %d", id)));
        return toDto(sponsor);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Sponsor sponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sponsor not found for id => %d", id)));
        sponsorRepository.setStatusInactive(sponsor.getId());
    }

    @Override
    @Transactional
    public SponsorDto update(Long id, SponsorDto sponsorDto) {
        Sponsor existingSponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Sponsor not found for id => %d", id)));

        existingSponsor.setType(sponsorDto.getType());
        existingSponsor.setLogo(sponsorDto.getLogo());
        existingSponsor.setProfile(sponsorDto.getProfile());

        existingSponsor.setAccount(accountRepository.findById(sponsorDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", sponsorDto.getAccount().getId()))));

        existingSponsor.setSubAccount(subAccountRepository.findById(sponsorDto.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for id => %d", sponsorDto.getSubAccount().getId()))));

        existingSponsor.setSponsorCompany(companyRepository.findById(sponsorDto.getSponsorCompany().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SponsorCompany not found for id => %d", sponsorDto.getSponsorCompany().getId()))));

        existingSponsor.setSponsorType(sponsorTypeRepository.findById(sponsorDto.getSponsorType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SponsorType not found for id => %d", sponsorDto.getSponsorType().getId()))));

        existingSponsor.setEvent(eventRepository.findById(sponsorDto.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", sponsorDto.getEvent().getId()))));

        Sponsor updatedSponsor = sponsorRepository.save(existingSponsor);
        return toDto(updatedSponsor);
    }

    public SponsorDto toDto(Sponsor sponsor) {
        return SponsorDto.builder()
                .id(sponsor.getId())
                .type(sponsor.getType())
                .logo(sponsor.getLogo())
                .profile(sponsor.getProfile())
                .status(sponsor.getStatus())
                .sponsorCompany(sponsor.getSponsorCompany())
                .subAccount(sponsor.getSubAccount())
                .account(sponsor.getAccount())
                .sponsorType(sponsor.getSponsorType())
                .event(sponsor.getEvent())
                .build();
    }

    public Sponsor toEntity(SponsorDto sponsorDto) {
        return Sponsor.builder()
                .id(sponsorDto.getId())
                .type(sponsorDto.getType())
                .logo(sponsorDto.getLogo())
                .profile(sponsorDto.getProfile())
                .status(sponsorDto.getStatus())
                .sponsorCompany(sponsorDto.getSponsorCompany())
                .subAccount(sponsorDto.getSubAccount())
                .account(sponsorDto.getAccount())
                .sponsorType(sponsorDto.getSponsorType())
                .event(sponsorDto.getEvent())
                .build();
    }
}
