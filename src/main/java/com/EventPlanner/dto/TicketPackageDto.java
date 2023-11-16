package com.EventPlanner.dto;

import com.EventPlanner.model.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketPackageDto {
    private Long id;

    @NotBlank(message = "ComboType cannot be blank")
    private String comboType;

    @NotNull(message = "TotalPrice cannot be null")
    @Positive(message = "TotalPrice must be a positive number")
    private Double totalPrice;

    @NotNull(message = "DiscountPrice cannot be null")
    @Positive(message = "DiscountPrice must be a positive number")
    private Double discountPrice;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    private Double price;

    private Boolean status;

    @NotNull(message = "Event cannot be null")
    private Event event;

}
