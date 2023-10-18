package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Location;
import com.EventPlanner.model.Role;
import com.EventPlanner.model.SubAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String website;
    private Boolean status;
    private Account account;
    private SubAccount subAccount;
    private Location location;
    private Set<Role> roles = new HashSet<>();
}
