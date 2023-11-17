package com.EventPlanner.dto;

import com.EventPlanner.model.Event;
import com.EventPlanner.model.Ticket;
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
public class DynamicFeeDto {
    private Long id;

    @NotBlank(message = "Ticket name cannot be blank")
    private String ticketName;

    @NotNull(message = "Quantity cannot be null")
    @PositiveOrZero(message = "Quantity must be a positive number or zero")
    private Double qty;

    @NotNull(message = "Standard price cannot be null")
    @Positive(message = "Standard price must be a positive number")
    private Double standardPrice;

    @NotBlank(message = "Discount type cannot be blank")
    private String discountType;

    @NotBlank(message = "Percent cannot be blank")
    private String percent;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be a positive number or zero")
    private Double price;

    @NotNull(message = "Discounted price cannot be null")
    @PositiveOrZero(message = "Discounted price must be a positive number or zero")
    private Double discountedPrice;

    @NotNull(message = "Max attendees cannot be null")
    @PositiveOrZero(message = "Max attendees must be a positive number or zero")
    private Integer maxAttendees;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateAndTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateAndTime;

    private Boolean status;

    @NotNull(message = "Event cannot be null")
    private Event event;

    @NotNull(message = "Ticket cannot be null")
    private Ticket ticket;
}
