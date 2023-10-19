package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubAccountDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private String website;
    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;

    @NotNull(message = "Location cannot be null")
    private Location location;
}
