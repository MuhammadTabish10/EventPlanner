package com.EventPlanner.dto;

import com.EventPlanner.model.Location;
import com.EventPlanner.model.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountryDto {
    private Long id;
    private String name;
    private Boolean status;
    private Location location;
    private List<Province> provinces;
}
