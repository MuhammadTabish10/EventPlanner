package com.EventPlanner.dto;

import com.EventPlanner.model.*;
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
public class SponsorDto {
    private Long id;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    @NotBlank(message = "Logo cannot be blank")
    private String logo;

    @NotBlank(message = "Profile cannot be blank")
    private String profile;

    private Boolean status;

    @NotNull(message = "Sponsor company cannot be null")
    private Company sponsorCompany;

    @NotNull(message = "Account cannot be null")
    private Account account;

    @NotNull(message = "SubAccount cannot be null")
    private SubAccount subAccount;

    @NotNull(message = "Sponsor type cannot be null")
    private SponsorType sponsorType;

    @NotNull(message = "Event cannot be null")
    private Event event;
}
