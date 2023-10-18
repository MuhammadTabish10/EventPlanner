package com.EventPlanner.dto;

import com.EventPlanner.model.Country;
import com.EventPlanner.model.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDto {
    private Long id;
    private String address1;
    private String address2;
    private String city;
    private Boolean status;
    private Country country;
    private Province province;
}
