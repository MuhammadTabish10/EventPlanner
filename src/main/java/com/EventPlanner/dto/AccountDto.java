package com.EventPlanner.dto;

import com.EventPlanner.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDto {
    private Long id;
    private LocalDateTime createdAt;
    private String name;
    private String phone;
    private String website;
    private Boolean status;
    private Industry industry;
    private Currency currency;
    private Location location;
    private Tag tag;
}
