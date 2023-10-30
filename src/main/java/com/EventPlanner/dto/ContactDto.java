package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.ContactType;
import com.EventPlanner.model.Location;
import com.EventPlanner.model.SubAccount;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactDto {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Customer name cannot be blank")
    private String customer;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private String profileDescription;
    private String bioDescription;
    private String headShotImage;
    private String website;
    private String twitter;
    private String instagram;
    private String linkedin;

    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;

    @NotNull(message = "SubAccount cannot be null")
    private SubAccount subAccount;

    @NotNull(message = "Location cannot be null")
    private Location location;

    @NotNull(message = "Contact type cannot be null")
    private ContactType contactType;
}
