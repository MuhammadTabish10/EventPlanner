package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Location;
import com.EventPlanner.model.Role;
import com.EventPlanner.model.SubAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    private String website;
    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;

    private SubAccount subAccount;

    @NotNull(message = "Location cannot be null")
    private Location location;

    private Set<Role> roles = new HashSet<>();
}
