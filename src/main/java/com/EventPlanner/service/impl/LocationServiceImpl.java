package com.EventPlanner.service.impl;

import com.EventPlanner.dto.IndustryDto;
import com.EventPlanner.dto.LocationDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Country;
import com.EventPlanner.model.Industry;
import com.EventPlanner.model.Location;
import com.EventPlanner.model.Province;
import com.EventPlanner.repository.CountryRepository;
import com.EventPlanner.repository.LocationRepository;
import com.EventPlanner.repository.ProvinceRepository;
import com.EventPlanner.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;

    public LocationServiceImpl(LocationRepository locationRepository, CountryRepository countryRepository, ProvinceRepository provinceRepository) {
        this.locationRepository = locationRepository;
        this.countryRepository = countryRepository;
        this.provinceRepository = provinceRepository;
    }

    @Override
    @Transactional
    public LocationDto save(LocationDto locationDto) {
        Location location = toEntity(locationDto);
        location.setStatus(true);

        Country country = countryRepository.findById(location.getCountry().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Country not found for id => %d", location.getCountry().getId())));

        Province province = provinceRepository.findById(location.getProvince().getId())
                .filter(country.getProvinces()::contains)
                .orElseThrow(() -> new RecordNotFoundException("The Province you selected is not part of the selected Country"));

        location.setProvince(province);
        location.setCountry(country);
        Location createdLocation = locationRepository.save(location);
        return toDto(createdLocation);
    }

    @Override
    public List<LocationDto> getAll() {
        List<Location> locationList = locationRepository.findAllInDesOrderByIdAndStatus();
        List<LocationDto> locationDtoList = new ArrayList<>();

        for (Location location : locationList) {
            LocationDto locationDto = toDto(location);
            locationDtoList.add(locationDto);
        }
        return locationDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedLocation(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Location> pageLocation = locationRepository.findAllInDesOrderByIdAndStatus(page);
        List<Location> locationList = pageLocation.getContent();

        List<LocationDto> locationDtoList = new ArrayList<>();
        for (Location location : locationList) {
            LocationDto locationDto = toDto(location);
            locationDtoList.add(locationDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(locationDtoList);
        paginationResponse.setPageNumber(pageLocation.getNumber());
        paginationResponse.setPageSize(pageLocation.getSize());
        paginationResponse.setTotalElements(pageLocation.getNumberOfElements());
        paginationResponse.setTotalPages(pageLocation.getTotalPages());
        paginationResponse.setLastPage(pageLocation.isLast());

        return paginationResponse;
    }

    @Override
    public LocationDto findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", id)));
        return toDto(location);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", id)));
        locationRepository.setStatusInactive(location.getId());
    }

    @Override
    @Transactional
    public LocationDto update(Long id, LocationDto locationDto) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", id)));

        existingLocation.setAddress1(locationDto.getAddress1());
        existingLocation.setAddress2(locationDto.getAddress2());
        existingLocation.setCity(locationDto.getCity());

        existingLocation.setCountry(countryRepository.findById(locationDto.getCountry().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Country not found for id => %d", locationDto.getCountry().getId()))));

        existingLocation.setProvince(provinceRepository.findById(locationDto.getProvince().getId())
                .filter(existingLocation.getCountry().getProvinces()::contains)
                .orElseThrow(() -> new RecordNotFoundException("The Province you selected is not part of the selected Country")));

        Location updatedLocation = locationRepository.save(existingLocation);
        return toDto(updatedLocation);
    }

    public LocationDto toDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .address1(location.getAddress1())
                .address2(location.getAddress2())
                .city(location.getCity())
                .status(location.getStatus())
                .province(location.getProvince())
                .country(location.getCountry())
                .build();
    }

    public Location toEntity(LocationDto locationDto) {
        return Location.builder()
                .id(locationDto.getId())
                .address1(locationDto.getAddress1())
                .address2(locationDto.getAddress2())
                .city(locationDto.getCity())
                .status(locationDto.getStatus())
                .province(locationDto.getProvince())
                .country(locationDto.getCountry())
                .build();
    }
}
