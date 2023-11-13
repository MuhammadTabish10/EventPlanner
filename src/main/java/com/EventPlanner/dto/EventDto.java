package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.EventType;
import com.EventPlanner.model.Venue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Event status cannot be blank")
    private String eventStatus;

    @PositiveOrZero(message = "Max registrations should be greater than or equal to zero")
    private Long maxRegistrations;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @FutureOrPresent(message = "Start date and time should be in the future or present")
    private LocalDateTime startDateAndTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @FutureOrPresent(message = "End date and time should be in the future or present")
    private LocalDateTime endDateAndTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Registration open date should be in the future or present")
    private LocalDate registrationOpenDate;

    @NotBlank(message = "Event lead cannot be blank")
    private String eventLead;

    @Pattern(regexp = "\\d{11}", message = "Event client contact should be a 11-digit number")
    private String eventClientContact;

    private Boolean status;

    @NotNull(message = "Event type cannot be null")
    private EventType eventType;

    @NotNull(message = "Venue cannot be null")
    private Venue venue;

    @NotNull(message = "Account cannot be null")
    private Account account;
}
