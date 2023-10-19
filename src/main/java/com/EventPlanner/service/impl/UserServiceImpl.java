package com.EventPlanner.service.impl;

import com.EventPlanner.dto.UserDto;
import com.EventPlanner.exception.RecordNotFoundException;
import com.EventPlanner.model.*;
import com.EventPlanner.repository.*;
import com.EventPlanner.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final SubAccountRepository subAccountRepository;
    private final LocationRepository locationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository, SubAccountRepository subAccountRepository, LocationRepository locationRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.subAccountRepository = subAccountRepository;
        this.locationRepository = locationRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userdto) {
        User user = toEntity(userdto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Account account = accountRepository.findById(user.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", user.getAccount().getId())));

        SubAccount subAccount = user.getSubAccount();
        if(subAccount != null){
            subAccount = subAccountRepository.findById(user.getSubAccount().getId())
                    .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for accountId => %d", user.getSubAccount().getId())));
        }

        Location location = locationRepository.findById(user.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", user.getLocation().getId())));

        Set<Role> roleList = new HashSet<>();
        for(Role role: user.getRoles()){
            roleRepository.findById(role.getId())
                    .orElseThrow(()-> new RecordNotFoundException("Role not found"));
            roleList.add(role);
        }

        user.setAccount(account);
        user.setSubAccount(subAccount);
        user.setLocation(location);
        user.setRoles(roleList);
        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> userList = userRepository.findAllInDesOrderByIdAndStatus();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = toDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return toDto(user);
        } else {
            throw new RecordNotFoundException(String.format("User not found for id => %d", id));
        }
    }

    @Override
    public UserDto findByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for name => %s", name)));
        return toDto(user);
    }

    @Override
    public List<UserDto> searchByName(String name) {
        List<User> userList = userRepository.findUserByName(name);
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = toDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", id)));
        userRepository.setStatusInactive(user.getId());
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", id)));

        existingUser.setName(userDto.getName());
        existingUser.setStatus(userDto.getStatus());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setWebsite(userDto.getWebsite());
        existingUser.setStatus(userDto.getStatus());

        existingUser.setAccount(accountRepository.findById(userDto.getAccount().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account not found for id => %d", userDto.getAccount().getId()))));

        if(userDto.getSubAccount() != null){
            existingUser.setSubAccount(subAccountRepository.findById(userDto.getSubAccount().getId())
                    .orElseThrow(() -> new RecordNotFoundException(String.format("SubAccount not found for accountId => %d", userDto.getSubAccount().getId()))));
        }

        existingUser.setLocation(locationRepository.findById(userDto.getLocation().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", userDto.getLocation().getId()))));

        existingUser.getRoles().removeIf(role -> !userDto.getRoles().contains(role));

        Set<Role> roleList = userDto.getRoles().stream()
                .map(role -> roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RecordNotFoundException("Role not found")))
                .collect(Collectors.toSet());

        existingUser.setRoles(roleList);
        User updatedUser = userRepository.save(existingUser);
        return toDto(updatedUser);
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .website(user.getWebsite())
                .status(user.getStatus())
                .account(user.getAccount())
                .subAccount(user.getSubAccount())
                .location(user.getLocation())
                .roles(user.getRoles())
                .build();
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .website(userDto.getWebsite())
                .status(userDto.getStatus())
                .account(userDto.getAccount())
                .subAccount(userDto.getSubAccount())
                .location(userDto.getLocation())
                .roles(userDto.getRoles())
                .build();
    }
}
