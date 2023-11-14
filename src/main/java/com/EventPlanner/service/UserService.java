package com.EventPlanner.service;

import com.EventPlanner.dto.PaginationResponse;
import com.EventPlanner.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto registerUser(UserDto userdto);
    List<UserDto> getAll();
    PaginationResponse getAllPaginatedUser(Integer pageNumber, Integer pageSize);
    PaginationResponse searchByName(String name, Integer pageNumber, Integer pageSize);
    UserDto findById(Long id);
    UserDto findByName(String name);
    void deleteById(Long id);
    UserDto update(Long id, UserDto userDto);
}
