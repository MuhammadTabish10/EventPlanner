package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.ProvinceDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Country;
import com.EventPlanner.model.Province;
import com.EventPlanner.repository.CountryRepository;
import com.EventPlanner.repository.ProvinceRepository;
import com.EventPlanner.service.ProvinceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final CountryRepository countryRepository;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository, CountryRepository countryRepository) {
        this.provinceRepository = provinceRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    @Transactional
    public ProvinceDto save(ProvinceDto provinceDto) {
        Province province = toEntity(provinceDto);
        province.setStatus(true);

        Country country = countryRepository.findById(province.getCountry().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Country not found for id => %d", province.getCountry().getId())));

        province.setCountry(country);
        Province createdProvince = provinceRepository.save(province);
        return toDto(createdProvince);
    }

    @Override
    public List<ProvinceDto> getAll() {
        List<Province> provinceList = provinceRepository.findAllInDesOrderByIdAndStatus();
        List<ProvinceDto> provinceDtoList = new ArrayList<>();

        for (Province province : provinceList) {
            ProvinceDto provinceDto = toDto(province);
            provinceDtoList.add(provinceDto);
        }
        return provinceDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedProvince(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Province> pageProvince = provinceRepository.findAllInDesOrderByIdAndStatus(page);
        List<Province> provinceList = pageProvince.getContent();

        List<ProvinceDto> provinceDtoList = new ArrayList<>();
        for (Province province : provinceList) {
            ProvinceDto provinceDto = toDto(province);
            provinceDtoList.add(provinceDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(provinceDtoList);
        paginationResponse.setPageNumber(pageProvince.getNumber());
        paginationResponse.setPageSize(pageProvince.getSize());
        paginationResponse.setTotalElements(pageProvince.getNumberOfElements());
        paginationResponse.setTotalPages(pageProvince.getTotalPages());
        paginationResponse.setLastPage(pageProvince.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Province> pageProvince = provinceRepository.findProvinceByName(name,page);
        List<Province> provinceList = pageProvince.getContent();

        List<ProvinceDto> provinceDtoList = new ArrayList<>();
        for (Province province : provinceList) {
            ProvinceDto provinceDto = toDto(province);
            provinceDtoList.add(provinceDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(provinceDtoList);
        paginationResponse.setPageNumber(pageProvince.getNumber());
        paginationResponse.setPageSize(pageProvince.getSize());
        paginationResponse.setTotalElements(pageProvince.getNumberOfElements());
        paginationResponse.setTotalPages(pageProvince.getTotalPages());
        paginationResponse.setLastPage(pageProvince.isLast());

        return paginationResponse;
    }

    @Override
    public ProvinceDto findById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Province not found for id => %d", id)));
        return toDto(province);
    }

    @Override
    public ProvinceDto findByName(String name) {
        Province province = provinceRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Province not found for name => %s", name)));
        return toDto(province);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Province not found for id => %d", id)));
        provinceRepository.setStatusInactive(province.getId());
    }

    @Override
    @Transactional
    public ProvinceDto update(Long id, ProvinceDto provinceDto) {
        Province existingProvince = provinceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Province not found for id => %d", id)));

        existingProvince.setName(provinceDto.getName());

        existingProvince.setCountry(countryRepository.findById(existingProvince.getCountry().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Country not found for id => %d", existingProvince.getCountry().getId()))));

        Province updatedProvince = provinceRepository.save(existingProvince);
        return toDto(updatedProvince);
    }

    public ProvinceDto toDto(Province province) {
        return ProvinceDto.builder()
                .id(province.getId())
                .name(province.getName())
                .status(province.getStatus())
                .country(province.getCountry())
                .build();
    }

    public Province toEntity(ProvinceDto provinceDto) {
        return Province.builder()
                .id(provinceDto.getId())
                .name(provinceDto.getName())
                .status(provinceDto.getStatus())
                .country(provinceDto.getCountry())
                .build();
    }
}
