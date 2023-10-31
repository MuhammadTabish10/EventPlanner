package com.EventPlanner.service.impl;

import com.EventPlanner.dto.TicketTypeDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.TicketType;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.TicketTypeRepository;
import com.EventPlanner.service.TicketTypeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {
    private final TicketTypeRepository ticketTypeRepository;
    private final AccountRepository accountRepository;

    public TicketTypeServiceImpl(TicketTypeRepository ticketTypeRepository, AccountRepository accountRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public TicketTypeDto save(TicketTypeDto ticketTypeDto) {
        TicketType ticketType = toEntity(ticketTypeDto);
        ticketType.setStatus(true);

        Account account = accountRepository.findById(ticketType.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", ticketType.getAccount().getId())));

        ticketType.setAccount(account);
        TicketType createdTicketType = ticketTypeRepository.save(ticketType);
        return toDto(createdTicketType);
    }

    @Override
    public List<TicketTypeDto> getAll() {
        List<TicketType> ticketTypeList = ticketTypeRepository.findAllInDesOrderByIdAndStatus();
        List<TicketTypeDto> ticketTypeDtoList = new ArrayList<>();

        for (TicketType ticketType : ticketTypeList) {
            TicketTypeDto ticketTypeDto = toDto(ticketType);
            ticketTypeDtoList.add(ticketTypeDto);
        }
        return ticketTypeDtoList;
    }

    @Override
    public TicketTypeDto findById(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Type not found for id => %d", id)));
        return toDto(ticketType);
    }

    @Override
    public TicketTypeDto findByType(String type) {
        TicketType ticketType = ticketTypeRepository.findByType(type)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Type not found for type => %s", type)));
        return toDto(ticketType);
    }

    @Override
    public List<TicketTypeDto> searchByType(String type) {
        List<TicketType> ticketTypeList = ticketTypeRepository.findTicketTypeByType(type);
        List<TicketTypeDto> ticketTypeDtoList = new ArrayList<>();

        for (TicketType ticketType : ticketTypeList) {
            TicketTypeDto ticketTypeDto = toDto(ticketType);
            ticketTypeDtoList.add(ticketTypeDto);
        }
        return ticketTypeDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Type not found for id => %d", id)));
        ticketTypeRepository.setStatusInactive(ticketType.getId());
    }

    @Override
    @Transactional
    public TicketTypeDto update(Long id, TicketTypeDto ticketTypeDto) {
        TicketType existingTicketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Ticket Type not found for id => %d", id)));

        existingTicketType.setType(ticketTypeDto.getType());

        existingTicketType.setAccount(accountRepository.findById(ticketTypeDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", ticketTypeDto.getAccount().getId()))));

        TicketType updatedTicketType = ticketTypeRepository.save(existingTicketType);
        return toDto(updatedTicketType);
    }

    public TicketTypeDto toDto(TicketType ticketType) {
        return TicketTypeDto.builder()
                .id(ticketType.getId())
                .type(ticketType.getType())
                .status(ticketType.getStatus())
                .account(ticketType.getAccount())
                .build();
    }

    public TicketType toEntity(TicketTypeDto ticketTypeDto) {
        return TicketType.builder()
                .id(ticketTypeDto.getId())
                .type(ticketTypeDto.getType())
                .status(ticketTypeDto.getStatus())
                .account(ticketTypeDto.getAccount())
                .build();
    }
}
