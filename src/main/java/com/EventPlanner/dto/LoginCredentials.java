package com.EventPlanner.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginCredentials {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
