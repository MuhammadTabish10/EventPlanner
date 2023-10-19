package com.EventPlanner.dto;

import com.EventPlanner.model.Country;
import com.EventPlanner.model.Province;
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
public class LocationDto {
    private Long id;

    @NotBlank(message = "Address1 cannot be blank")
    private String address1;

    private String address2;

    @NotBlank(message = "City cannot be blank")
    private String city;
    private Boolean status;

    @NotNull(message = "Country cannot be null")
    private Country country;

    @NotNull(message = "Province cannot be null")
    private Province province;
}
