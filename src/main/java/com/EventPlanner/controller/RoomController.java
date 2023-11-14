package com.EventPlanner.controller;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.RoomDto;
import com.EventPlanner.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.save(roomDto));
    }

    @GetMapping("/room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RoomDto>> getAllRoom() {
        List<RoomDto> roomDtoList = roomService.getAll();
        return ResponseEntity.ok(roomDtoList);
    }

    @GetMapping("/room/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllPaginatedRoom(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = roomService.getAllPaginatedRoom(pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/room/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        RoomDto roomDto = roomService.findById(id);
        return ResponseEntity.ok(roomDto);
    }

    @GetMapping("/room/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomDto> getRoomByName(@PathVariable String name) {
        RoomDto roomDto = roomService.findByName(name);
        return ResponseEntity.ok(roomDto);
    }

    @GetMapping("/room/names/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginationResponse> getAllRoomByName(
            @PathVariable String name,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
    ) {
        PaginationResponse paginationResponse = roomService.searchByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(paginationResponse);
    }

    @DeleteMapping("/room/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/room/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDto roomDto) {
        RoomDto updatedRoomDto = roomService.update(id, roomDto);
        return ResponseEntity.ok(updatedRoomDto);
    }
}
