package com.EventPlanner.dto;

import com.EventPlanner.model.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProvinceDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    private Boolean status;

    @NotNull(message = "Country cannot be null")
    private Country country;
}
