package com.EventPlanner.service.impl;

import com.EventPlanner.dto.RoomDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.Account;
import com.EventPlanner.model.Room;
import com.EventPlanner.model.Venue;
import com.EventPlanner.repository.AccountRepository;
import com.EventPlanner.repository.RoomRepository;
import com.EventPlanner.repository.VenueRepository;
import com.EventPlanner.service.RoomService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final VenueRepository venueRepository;
    private final AccountRepository accountRepository;

    public RoomServiceImpl(RoomRepository roomRepository, VenueRepository venueRepository, AccountRepository accountRepository) {
        this.roomRepository = roomRepository;
        this.venueRepository = venueRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    @Transactional
    public RoomDto save(RoomDto roomDto) {
        Room room = toEntity(roomDto);
        room.setStatus(true);

        Account account = accountRepository.findById(room.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", room.getAccount().getId())));

        Venue venue = venueRepository.findById(room.getVenue().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", room.getVenue().getId())));


        room.setAccount(account);
        room.setVenue(venue);
        Room createdRoom = roomRepository.save(room);
        return toDto(createdRoom);
    }

    @Override
    public List<RoomDto> getAll() {
        List<Room> roomList = roomRepository.findAllInDesOrderByIdAndStatus();
        List<RoomDto> roomDtoList = new ArrayList<>();

        for (Room room : roomList) {
            RoomDto roomDto = toDto(room);
            roomDtoList.add(roomDto);
        }
        return roomDtoList;
    }

    @Override
    public RoomDto findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Room not found for id => %d", id)));
        return toDto(room);
    }

    @Override
    public RoomDto findByName(String name) {
        Room room = roomRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Room not found for name => %s", name)));
        return toDto(room);
    }

    @Override
    public List<RoomDto> searchByName(String name) {
        List<Room> roomList = roomRepository.findRoomByName(name);
        List<RoomDto> roomDtoList = new ArrayList<>();

        for (Room room : roomList) {
            RoomDto roomDto = toDto(room);
            roomDtoList.add(roomDto);
        }
        return roomDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Room not found for id => %d", id)));
        roomRepository.setStatusInactive(room.getId());
    }

    @Override
    @Transactional
    public RoomDto update(Long id, RoomDto roomDto) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Room not found for id => %d", id)));

        existingRoom.setName(roomDto.getName());
        existingRoom.setCapacity(roomDto.getCapacity());
        existingRoom.setAttachments(roomDto.getAttachments());
        existingRoom.setStatus(roomDto.getStatus());

        existingRoom.setAccount(accountRepository.findById(roomDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", roomDto.getAccount().getId()))));

        existingRoom.setVenue(venueRepository.findById(roomDto.getVenue().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Venue not found for id => %d", roomDto.getVenue().getId()))));

        Room updatedRoom = roomRepository.save(existingRoom);
        return toDto(updatedRoom);
    }

    public RoomDto toDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .createdAt(room.getCreatedAt())
                .name(room.getName())
                .capacity(room.getCapacity())
                .attachments(room.getAttachments())
                .status(room.getStatus())
                .account(room.getAccount())
                .venue(room.getVenue())
                .build();
    }

    public Room toEntity(RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.getId())
                .createdAt(roomDto.getCreatedAt())
                .name(roomDto.getName())
                .capacity(roomDto.getCapacity())
                .attachments(roomDto.getAttachments())
                .status(roomDto.getStatus())
                .account(roomDto.getAccount())
                .venue(roomDto.getVenue())
                .build();
    }
}
