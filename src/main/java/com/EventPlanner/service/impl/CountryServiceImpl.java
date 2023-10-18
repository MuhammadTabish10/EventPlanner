package com.EventPlanner.service.impl;

import com.EventPlanner.dto.CountryDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Country;
import com.EventPlanner.model.Location;
import com.EventPlanner.model.Province;
import com.EventPlanner.repository.CountryRepository;
import com.EventPlanner.repository.LocationRepository;
import com.EventPlanner.repository.ProvinceRepository;
import com.EventPlanner.service.CountryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;
    private final LocationRepository locationRepository;

    public CountryServiceImpl(CountryRepository countryRepository, ProvinceRepository provinceRepository, LocationRepository locationRepository) {
        this.countryRepository = countryRepository;
        this.provinceRepository = provinceRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public CountryDto save(CountryDto countryDto) {
        Country country = toEntity(countryDto);
        country.setStatus(true);
//        Location location = locationRepository.findById(country.getLocation().getId())
//                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", country.getLocation().getId())));
//        country.setLocation(location);
        Country createdCountry = countryRepository.save(country);

//        List<Province> provinces = country.getProvinces();
//        if(provinces != null && !provinces.isEmpty()){
//            for(Province province : provinces){
//                province.setCountry(createdCountry);
//                province.setStatus();
//                province.setLocation(locationRepository.findById(country.getLocation().getId())
//                        .orElseThrow(() -> new RecordNotFoundException("Location not found for id => %d"+ country.getLocation().getId())));
//            }
//        }

        return toDto(createdCountry);
    }

    @Override
    public List<CountryDto> getAll() {
        List<Country> countryList = countryRepository.findAll();
        List<CountryDto> countryDtoList = new ArrayList<>();

        for (Country country : countryList) {
            CountryDto countryDto = toDto(country);
            countryDtoList.add(countryDto);
        }
        return countryDtoList;
    }

    @Override
    public CountryDto findById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Country not found for id => %d", id)));
        return toDto(country);
    }

    @Override
    public CountryDto findByName(String name) {
        Country country = countryRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Country not found for name => %s", name)));
        return toDto(country);
    }

    @Override
    public List<CountryDto> searchByName(String name) {
        List<Country> countryList = countryRepository.findCountriesByName(name);
        List<CountryDto> countryDtoList = new ArrayList<>();

        for (Country country : countryList) {
            CountryDto countryDto = toDto(country);
            countryDtoList.add(countryDto);
        }
        return countryDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Country not found for id => %d", id)));
        countryRepository.setStatusInactive(country.getId());
    }

    @Override
    @Transactional
    public CountryDto update(Long id, CountryDto countryDto) {
        Country existingCountry = countryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Country not found for id => %d", id)));

        existingCountry.setName(countryDto.getName());
        existingCountry.setStatus(countryDto.getStatus());

        Set<Long> provinceIds = countryDto.getProvinces()
                .stream()
                .map(Province::getId)
                .collect(Collectors.toSet());

        existingCountry.getProvinces().removeIf(province -> !provinceIds.contains(province.getId()));

        if (!provinceIds.isEmpty()) {
            List<Province> updatedProvinces = provinceRepository.findAllById(provinceIds);
            existingCountry.setProvinces(updatedProvinces);
        }

        Country updatedCountry = countryRepository.save(existingCountry);
        return toDto(updatedCountry);
    }

    public CountryDto toDto(Country country) {
        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .status(country.getStatus())
                .provinces(country.getProvinces())
                .location(country.getLocation())
                .build();
    }

    public Country toEntity(CountryDto countryDto) {
        return Country.builder()
                .id(countryDto.getId())
                .name(countryDto.getName())
                .status(countryDto.getStatus())
                .provinces(countryDto.getProvinces())
                .location(countryDto.getLocation())
                .build();
    }
}
