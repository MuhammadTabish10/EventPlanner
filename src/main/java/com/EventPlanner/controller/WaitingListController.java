package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.WaitingListDto;
import com.EventPlanner.service.WaitingListService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WaitingListController {
    private final WaitingListService waitingListService;

    public WaitingListController(WaitingListService waitingListService) {
        this.waitingListService = waitingListService;
    }

    @PostMapping("/waiting_list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<WaitingListDto> createWaitingList(@Valid @RequestBody WaitingListDto waitingListDto) {
        return ResponseEntity.ok(waitingListService.save(waitingListDto));
    }

    @GetMapping("/waiting_list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<WaitingListDto>> getAllWaitingList() {
        List<WaitingListDto> waitingListDtoList = waitingListService.getAll();
        return ResponseEntity.ok(waitingListDtoList);
    }

    @GetMapping("/waiting_list/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedWaitingList(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = waitingListService.getAllPaginatedWaitingList(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/waiting_list/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<WaitingListDto> getWaitingListById(@PathVariable Long id) {
        WaitingListDto waitingListDto = waitingListService.findById(id);
        return ResponseEntity.ok(waitingListDto);
    }

    @GetMapping("/waiting_list/first-name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<WaitingListDto> getWaitingListByFirstName(@PathVariable String name) {
        WaitingListDto waitingListDto = waitingListService.findByFirstName(name);
        return ResponseEntity.ok(waitingListDto);
    }

    @GetMapping("/waiting_list/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllWaitingListByFirstName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = waitingListService.searchByFirstName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/waiting_list/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteWaitingList(@PathVariable Long id) {
        waitingListService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/waiting_list/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<WaitingListDto> updateWaitingList(@PathVariable Long id, @Valid @RequestBody WaitingListDto waitingListDto) {
        WaitingListDto updatedWaitingListDto = waitingListService.update(id, waitingListDto);
        return ResponseEntity.ok(updatedWaitingListDto);
    }
}
