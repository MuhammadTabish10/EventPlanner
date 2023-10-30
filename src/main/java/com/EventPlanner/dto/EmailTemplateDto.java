package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.SubAccount;
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
public class EmailTemplateDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private Boolean status;

    @NotNull(message = "Account cannot be null")
    private Account account;

    @NotNull(message = "SubAccount cannot be null")
    private SubAccount subAccount;
}
