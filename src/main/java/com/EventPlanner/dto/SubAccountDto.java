package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Location;
import com.EventPlanner.model.SubAccountPK;
import com.EventPlanner.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubAccountDto {
    private SubAccountPK id;
    private LocalDateTime createdAt;
    private String phone;
    private String website;
    private Boolean status;
    private Account account;
    private Location location;
}
