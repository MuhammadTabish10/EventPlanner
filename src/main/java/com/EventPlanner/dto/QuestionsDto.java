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
public class QuestionsDto {
    private Long id;

    @NotBlank(message = "Question cannot be blank")
    private String question;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    private String description;
    private Boolean status;

    @NotNull(message = "isSystem cannot be null")
    private Boolean isSystem;

    @NotNull(message = "appliesToOurAccount cannot be null")
    private Boolean appliesToOurAccount;

    @NotNull(message = "Account cannot be null")
    private Account account;
}
