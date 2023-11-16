package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.Room;
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
public class SessionDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    @NotNull(message = "Capacity cannot be null")
    @Positive(message = "Capacity must be a positive number")
    private Double capacity;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @FutureOrPresent(message = "Start date and time must be in the future or present")
    private LocalDateTime startDateAndTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Future(message = "End date and time must be in the future")
    private LocalDateTime endDateAndTime;

    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;

    @NotNull(message = "Room cannot be null")
    private Room room;

    @NotNull(message = "Event cannot be null")
    private Event event;
}
