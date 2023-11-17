package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Event;
import com.EventPlanner.model.TicketPackage;
import com.EventPlanner.model.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "SKU cannot be blank")
    private String sku;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "isTable cannot be null")
    private Boolean isTable;

    @PositiveOrZero(message = "MaxSeats must be a positive number or zero")
    private Integer maxSeats;

    @NotNull(message = "isTable cannot be null")
    private Integer restrictions;

    private Boolean status;

    @NotNull(message = "TicketPackage cannot be null")
    private TicketPackage ticketPackage;

    @NotNull(message = "Account cannot be null")
    private Account account;

    @NotNull(message = "Event cannot be null")
    private Event event;

    @NotNull(message = "TicketType cannot be null")
    private TicketType ticketType;
}
