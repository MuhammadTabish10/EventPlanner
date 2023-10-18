package com.EventPlanner.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginCredentials {
    private String name;
    private String password;
}
