package com.EventPlanner.dto;

import com.EventPlanner.model.Event;
import com.EventPlanner.model.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardPriceDto {
    private Long id;
    private String ticketName;
    private Double price;
    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateAndTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateAndTime;

    private Event event;
    private Ticket ticket;
}
