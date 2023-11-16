package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.SubAccount;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaitingListDto {
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Positive(message = "Wish list number must be a positive integer")
    private Integer wishListNumber;

    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;

    @NotNull(message = "SubAccount cannot be null")
    private SubAccount subAccount;

    @NotNull(message = "Event cannot be null")
    private Event event;
}
