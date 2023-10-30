package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Venue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDto {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Capacity cannot be null")
    private Double capacity;

    @NotNull(message = "Attachments cannot be null")
    private String attachments;

    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;

    @NotNull(message = "Venue cannot be null")
    private Venue venue;
}
