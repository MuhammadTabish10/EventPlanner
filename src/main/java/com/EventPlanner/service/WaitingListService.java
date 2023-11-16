package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.WaitingListDto;

import java.util.List;

public interface WaitingListService {
    WaitingListDto save(WaitingListDto waitingListDto);
    List<WaitingListDto> getAll();
    PaginationResponse getAllPaginatedWaitingList(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByFirstName(String firstName, Integer pageNumber, Integer pageSize);
    WaitingListDto findById(Long id);
    WaitingListDto findByFirstName(String firstName);
    void deleteById(Long id);
    WaitingListDto update(Long id, WaitingListDto waitingListDto);
}
