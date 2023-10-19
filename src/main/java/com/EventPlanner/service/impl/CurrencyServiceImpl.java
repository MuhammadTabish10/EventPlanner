package com.EventPlanner.service.impl;

import com.EventPlanner.dto.CurrencyDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Currency;
import com.EventPlanner.repository.CurrencyRepository;
import com.EventPlanner.service.CurrencyService;
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
    public List<CurrencyDto> searchByName(String name) {
        List<Currency> currencyList = currencyRepository.findCurrencyByName(name);
        List<CurrencyDto> currencyDtoList = new ArrayList<>();

        for (Currency currency : currencyList) {
            CurrencyDto currencyDto = toDto(currency);
            currencyDtoList.add(currencyDto);
        }
        return currencyDtoList;
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
        existingCurrency.setStatus(currencyDto.getStatus());

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
