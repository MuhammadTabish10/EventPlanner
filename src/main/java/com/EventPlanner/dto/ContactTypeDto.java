package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
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
public class ContactTypeDto {
    private Long id;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;
}
