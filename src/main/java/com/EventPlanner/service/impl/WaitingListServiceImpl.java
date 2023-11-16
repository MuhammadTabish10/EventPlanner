package com.EventPlanner.service.impl;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.WaitingListDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.SubAccount;
import com.EventPlanner.model.WaitingList;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.EventRepository;
import com.EventPlanner.repository.SubAccountRepository;
import com.EventPlanner.repository.WaitingListRepository;
import com.EventPlanner.service.WaitingListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class WaitingListServiceImpl implements WaitingListService {

    private final WaitingListRepository waitingListRepository;
    private final AccountRepository accountRepository;
    private final SubAccountRepository subAccountRepository;
    private final EventRepository eventRepository;

    public WaitingListServiceImpl(WaitingListRepository waitingListRepository, AccountRepository accountRepository, SubAccountRepository subAccountRepository, EventRepository eventRepository) {
        this.waitingListRepository = waitingListRepository;
        this.accountRepository = accountRepository;
        this.subAccountRepository = subAccountRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public WaitingListDto save(WaitingListDto waitingListDto) {
        WaitingList waitingList = toEntity(waitingListDto);
        waitingList.setStatus(true);

        Account account = accountRepository.findById(waitingList.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", waitingList.getAccount().getId())));

        SubAccount subAccount = subAccountRepository.findById(waitingList.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for id => %d", waitingList.getSubAccount().getId())));

        Event event = eventRepository.findById(waitingList.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", waitingList.getEvent().getId())));

        waitingList.setAccount(account);
        waitingList.setSubAccount(subAccount);
        waitingList.setEvent(event);
        WaitingList createdWaitingList = waitingListRepository.save(waitingList);
        return toDto(createdWaitingList);
    }

    @Override
    public List<WaitingListDto> getAll() {
        List<WaitingList> waitingList = waitingListRepository.findAllInDesOrderByIdAndStatus();
        List<WaitingListDto> waitingListDtoList = new ArrayList<>();

        for (WaitingList waitingList1 : waitingList) {
            WaitingListDto waitingListDto = toDto(waitingList1);
            waitingListDtoList.add(waitingListDto);
        }
        return waitingListDtoList;
    }

    @Override
    public PaginationResponse getAllPaginatedWaitingList(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<WaitingList> pageWaitingList = waitingListRepository.findAllInDesOrderByIdAndStatus(page);
        List<WaitingList> waitingListList = pageWaitingList.getContent();

        List<WaitingListDto> waitingListDtoList = new ArrayList<>();
        for (WaitingList waitingList : waitingListList) {
            WaitingListDto waitingListDto = toDto(waitingList);
            waitingListDtoList.add(waitingListDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(waitingListDtoList);
        paginationResponse.setPageNumber(pageWaitingList.getNumber());
        paginationResponse.setPageSize(pageWaitingList.getSize());
        paginationResponse.setTotalElements(pageWaitingList.getNumberOfElements());
        paginationResponse.setTotalPages(pageWaitingList.getTotalPages());
        paginationResponse.setLastPage(pageWaitingList.isLast());

        return paginationResponse;
    }

    @Override
    public PaginationResponse searchByFirstName(String firstName, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<WaitingList> pageWaitingList = waitingListRepository.searchByFirstName(firstName,page);
        List<WaitingList> waitingListList = pageWaitingList.getContent();

        List<WaitingListDto> waitingListDtoList = new ArrayList<>();
        for (WaitingList waitingList : waitingListList) {
            WaitingListDto waitingListDto = toDto(waitingList);
            waitingListDtoList.add(waitingListDto);
        }

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(waitingListDtoList);
        paginationResponse.setPageNumber(pageWaitingList.getNumber());
        paginationResponse.setPageSize(pageWaitingList.getSize());
        paginationResponse.setTotalElements(pageWaitingList.getNumberOfElements());
        paginationResponse.setTotalPages(pageWaitingList.getTotalPages());
        paginationResponse.setLastPage(pageWaitingList.isLast());

        return paginationResponse;
    }

    @Override
    public WaitingListDto findById(Long id) {
        WaitingList waitingList = waitingListRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Waiting List not found for id => %d", id)));
        return toDto(waitingList);
    }

    @Override
    public WaitingListDto findByFirstName(String firstName) {
        WaitingList waitingList = waitingListRepository.findByFirstName(firstName)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Waiting List not found for first Name => %s", firstName)));
        return toDto(waitingList);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        WaitingList waitingList = waitingListRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Waiting List not found for id => %d", id)));
        waitingListRepository.setStatusInactive(waitingList.getId());
    }

    @Override
    @Transactional
    public WaitingListDto update(Long id, WaitingListDto waitingListDto) {
        WaitingList existingWaitingList = waitingListRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Waiting List not found for id => %d", id)));

        existingWaitingList.setFirstName(waitingListDto.getFirstName());
        existingWaitingList.setLastName(waitingListDto.getLastName());
        existingWaitingList.setEmail(waitingListDto.getEmail());
        existingWaitingList.setPhone(waitingListDto.getPhone());
        existingWaitingList.setDate(waitingListDto.getDate());
        existingWaitingList.setWishListNumber(waitingListDto.getWishListNumber());

        existingWaitingList.setAccount(accountRepository.findById(waitingListDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", waitingListDto.getAccount().getId()))));

        existingWaitingList.setSubAccount(subAccountRepository.findById(waitingListDto.getSubAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for id => %d", waitingListDto.getSubAccount().getId()))));

        existingWaitingList.setEvent(eventRepository.findById(waitingListDto.getEvent().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Event not found for id => %d", waitingListDto.getEvent().getId()))));

        WaitingList updatedWaitingList = waitingListRepository.save(existingWaitingList);
        return toDto(updatedWaitingList);
    }

    public WaitingListDto toDto(WaitingList waitingList) {
        return WaitingListDto.builder()
                .id(waitingList.getId())
                .firstName(waitingList.getFirstName())
                .lastName(waitingList.getLastName())
                .email(waitingList.getEmail())
                .phone(waitingList.getPhone())
                .date(waitingList.getDate())
                .wishListNumber(waitingList.getWishListNumber())
                .status(waitingList.getStatus())
                .account(waitingList.getAccount())
                .subAccount(waitingList.getSubAccount())
                .event(waitingList.getEvent())
                .build();
    }

    public WaitingList toEntity(WaitingListDto waitingListDto) {
        return WaitingList.builder()
                .id(waitingListDto.getId())
                .firstName(waitingListDto.getFirstName())
                .lastName(waitingListDto.getLastName())
                .email(waitingListDto.getEmail())
                .phone(waitingListDto.getPhone())
                .date(waitingListDto.getDate())
                .wishListNumber(waitingListDto.getWishListNumber())
                .status(waitingListDto.getStatus())
                .account(waitingListDto.getAccount())
                .subAccount(waitingListDto.getSubAccount())
                .event(waitingListDto.getEvent())
                .build();
    }
}
