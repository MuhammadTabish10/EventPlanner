package com.EventPlanner.dto;

import com.EventPlanner.model.*;
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
public class CompanyDto {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String name;
    private String profile;
    private String website;
    private String twitter;
    private String instagram;
    private String linkedin;
    private Boolean status;
    private Account account;
    private SubAccount subAccount;
    private Location location;
    private Tag tag;
    private CompanyType companyType;
    private Venue venue;
}
