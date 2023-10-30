package com.EventPlanner.service.impl;

import com.EventPlanner.dto.CompanyDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.*;
import com.EventPlanner.repository.*;
import com.EventPlanner.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyTypeRepository companyTypeRepository;
    private final TagRepository tagRepository;
    private final VenueRepository venueRepository;
    private final AccountRepository accountRepository;
    private final SubAccountRepository subAccountRepository;
    private final LocationRepository locationRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyTypeRepository companyTypeRepository, TagRepository tagRepository, VenueRepository venueRepository, AccountRepository accountRepository, SubAccountRepository subAccountRepository, LocationRepository locationRepository) {
        this.companyRepository = companyRepository;
        this.companyTypeRepository = companyTypeRepository;
        this.tagRepository = tagRepository;
        this.venueRepository = venueRepository;
        this.accountRepository = accountRepository;
        this.subAccountRepository = subAccountRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public CompanyDto save(CompanyDto companyDto) {
        Company company = toEntity(companyDto);
        company.setStatus(true);

        Account account = accountRepository.findById(company.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", company.getAccount().getId())));

        SubAccount subAccount = subAccountRepository.findById(company.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for id => %d", company.getSubAccount().getId())));

        Location location = locationRepository.findById(company.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", company.getLocation().getId())));

        Venue venue = venueRepository.findById(company.getVenue().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", company.getVenue().getId())));

        Tag tag = tagRepository.findById(company.getTag().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Tag not found for id => %d", company.getTag().getId())));

        CompanyType companyType = companyTypeRepository.findById(company.getCompanyType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company Type not found for id => %d", company.getCompanyType().getId())));

        company.setAccount(account);
        company.setSubAccount(subAccount);
        company.setLocation(location);
        company.setVenue(venue);
        company.setTag(tag);
        company.setCompanyType(companyType);

        Company createdCompany = companyRepository.save(company);
        return toDto(createdCompany);
    }

    @Override
    public List<CompanyDto> getAll() {
        List<Company> companyList = companyRepository.findAllInDesOrderByIdAndStatus();
        List<CompanyDto> companyDtoList = new ArrayList<>();

        for (Company company : companyList) {
            CompanyDto companyDto = toDto(company);
            companyDtoList.add(companyDto);
        }
        return companyDtoList;
    }

    @Override
    public CompanyDto findById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company not found for id => %d", id)));
        return toDto(company);
    }

    @Override
    public CompanyDto findByName(String name) {
        Company company = companyRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company not found for name => %s", name)));
        return toDto(company);
    }

    @Override
    public List<CompanyDto> searchByName(String name) {
        List<Company> companyList = companyRepository.findCompanyByName(name);
        List<CompanyDto> companyDtoList = new ArrayList<>();

        for (Company company : companyList) {
            CompanyDto companyDto = toDto(company);
            companyDtoList.add(companyDto);
        }
        return companyDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company not found for id => %d", id)));
        companyRepository.setStatusInactive(company.getId());
    }

    @Override
    @Transactional
    public CompanyDto update(Long id, CompanyDto companyDto) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company not found for id => %d", id)));

        existingCompany.setName(companyDto.getName());
        existingCompany.setProfile(companyDto.getProfile());
        existingCompany.setWebsite(companyDto.getWebsite());
        existingCompany.setTwitter(companyDto.getTwitter());
        existingCompany.setInstagram(companyDto.getInstagram());
        existingCompany.setLinkedin(companyDto.getLinkedin());
        existingCompany.setStatus(companyDto.getStatus());

        existingCompany.setAccount(accountRepository.findById(companyDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", companyDto.getAccount().getId()))));

        existingCompany.setSubAccount(subAccountRepository.findById(companyDto.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for id => %d", companyDto.getSubAccount().getId()))));

        existingCompany.setLocation(locationRepository.findById(companyDto.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", companyDto.getLocation().getId()))));

        existingCompany.setVenue(venueRepository.findById(companyDto.getVenue().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", companyDto.getVenue().getId()))));

        existingCompany.setTag(tagRepository.findById(companyDto.getTag().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Tag not found for id => %d", companyDto.getTag().getId()))));

        existingCompany.setCompanyType(companyTypeRepository.findById(companyDto.getCompanyType().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company Type not found for id => %d", companyDto.getCompanyType().getId()))));

        Company updatedCompany = companyRepository.save(existingCompany);
        return toDto(updatedCompany);
    }


    public CompanyDto toDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .createdAt(company.getCreatedAt())
                .name(company.getName())
                .profile(company.getProfile())
                .website(company.getWebsite())
                .twitter(company.getTwitter())
                .instagram(company.getInstagram())
                .linkedin(company.getLinkedin())
                .status(company.getStatus())
                .account(company.getAccount())
                .subAccount(company.getSubAccount())
                .tag(company.getTag())
                .location(company.getLocation())
                .venue(company.getVenue())
                .companyType(company.getCompanyType())
                .build();
    }

    public Company toEntity(CompanyDto companyDto) {
        return Company.builder()
                .id(companyDto.getId())
                .createdAt(companyDto.getCreatedAt())
                .name(companyDto.getName())
                .profile(companyDto.getProfile())
                .website(companyDto.getWebsite())
                .twitter(companyDto.getTwitter())
                .instagram(companyDto.getInstagram())
                .linkedin(companyDto.getLinkedin())
                .status(companyDto.getStatus())
                .account(companyDto.getAccount())
                .subAccount(companyDto.getSubAccount())
                .tag(companyDto.getTag())
                .location(companyDto.getLocation())
                .venue(companyDto.getVenue())
                .companyType(companyDto.getCompanyType())
                .build();
    }
}
