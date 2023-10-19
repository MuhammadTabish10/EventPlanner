package com.EventPlanner.dto;

import com.EventPlanner.model.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountryDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    private Boolean status;

    private List<Province> provinces;
}
