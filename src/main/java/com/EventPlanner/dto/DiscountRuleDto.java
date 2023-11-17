package com.EventPlanner.dto;

import com.EventPlanner.model.Event;
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
public class DiscountRuleDto {
    private Long id;

    @NotBlank(message = "Discount code cannot be blank")
    private String discountCode;

    @NotBlank(message = "Discount type cannot be blank")
    private String discountType;

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
}
