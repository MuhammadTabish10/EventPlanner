package com.EventPlanner.dto;

import com.EventPlanner.model.Currency;
import com.EventPlanner.model.Industry;
import com.EventPlanner.model.Location;
import com.EventPlanner.model.Tag;
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
public class AccountDto {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private String website;
    private Boolean status;

    @NotNull(message = "Industry cannot be null")
    private Industry industry;

    @NotNull(message = "Currency cannot be null")
    private Currency currency;

    @NotNull(message = "Location cannot be null")
    private Location location;

    @NotNull(message = "Tag cannot be null")
    private Tag tag;
}
