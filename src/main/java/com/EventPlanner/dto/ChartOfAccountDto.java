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
public class ChartOfAccountDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "GL Account cannot be blank")
    private String glAccount;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    private String description;
    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;
}
