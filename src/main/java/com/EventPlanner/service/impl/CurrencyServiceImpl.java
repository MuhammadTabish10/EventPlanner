package com.EventPlanner.service.impl;

import com.EventPlanner.dto.ContactTypeDto;
import com.EventPlanner.dto.CurrencyDto;
import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.ContactType;
import com.EventPlanner.model.Currency;
import com.EventPlanner.repository.CurrencyRepository;
import com.EventPlanner.service.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public CurrencyDto save(CurrencyDto currencyDto) {
        Currency currency = toEntity(currencyDto);
        currency.setStatus(true);
        Currency createdCurrency = currencyRepository.save(currency);
        return toDto(createdCurrency);
    }

    @Override
    public List<CurrencyDto> getAll() {
        List<Currency> currencyList = currencyRepository.findAllInDesOrderByIdAndStatus();
        List<CurrencyDto> currencyDtoList = new ArrayList<>();

        for (Currency currency : currencyList) {
            CurrencyDto currencyDto = toDto(currency);
            currencyDtoList.add(currencyDto);
        }
        return currencyDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedCurrency(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Currency> pageCurrency = currencyRepository.findAllInDesOrderByIdAndStatus(page);
        List<Currency> currencyList = pageCurrency.getContent();

        List<CurrencyDto> currencyDtoList = new ArrayList<>();
        for (Currency currency : currencyList) {
            CurrencyDto currencyDto = toDto(currency);
            currencyDtoList.add(currencyDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(currencyDtoList);
        paginationResponse.setPageNumber(pageCurrency.getNumber());
        paginationResponse.setPageSize(pageCurrency.getSize());
        paginationResponse.setTotalElements(pageCurrency.getNumberOfElements());
        paginationResponse.setTotalPages(pageCurrency.getTotalPages());
        paginationResponse.setLastPage(pageCurrency.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Currency> pageCurrency = currencyRepository.findCurrencyByName(name,page);
        List<Currency> currencyList = pageCurrency.getContent();

        List<CurrencyDto> currencyDtoList = new ArrayList<>();
        for (Currency currency : currencyList) {
            CurrencyDto currencyDto = toDto(currency);
            currencyDtoList.add(currencyDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(currencyDtoList);
        paginationResponse.setPageNumber(pageCurrency.getNumber());
        paginationResponse.setPageSize(pageCurrency.getSize());
        paginationResponse.setTotalElements(pageCurrency.getNumberOfElements());
        paginationResponse.setTotalPages(pageCurrency.getTotalPages());
        paginationResponse.setLastPage(pageCurrency.isLast());

        return paginationResponse;
    }

    @Override
    public CurrencyDto findById(Long id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Currency not found for id => %d", id)));
        return toDto(currency);
    }

    @Override
    public CurrencyDto findByName(String name) {
        Currency currency = currencyRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Currency not found for name => %s", name)));
        return toDto(currency);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Currency not found for id => %d", id)));
        currencyRepository.setStatusInactive(currency.getId());
    }

    @Override
    @Transactional
    public CurrencyDto update(Long id, CurrencyDto currencyDto) {
        Currency existingCurrency = currencyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Currency not found for id => %d", id)));

        existingCurrency.setName(currencyDto.getName());

        Currency updatedCurrency = currencyRepository.save(existingCurrency);
        return toDto(updatedCurrency);
    }

    public CurrencyDto toDto(Currency currency) {
        return CurrencyDto.builder()
                .id(currency.getId())
                .name(currency.getName())
                .status(currency.getStatus())
                .build();
    }

    public Currency toEntity(CurrencyDto currencyDto) {
        return Currency.builder()
                .id(currencyDto.getId())
                .name(currencyDto.getName())
                .status(currencyDto.getStatus())
                .build();
    }
}
