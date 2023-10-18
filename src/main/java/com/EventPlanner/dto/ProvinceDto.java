package com.EventPlanner.dto;

import com.EventPlanner.model.Country;
import com.EventPlanner.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProvinceDto {
    private Long id;
    private String name;
    private Boolean status;
    private Country country;
    private Location location;
}
