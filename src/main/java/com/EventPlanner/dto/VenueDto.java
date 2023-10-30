package com.EventPlanner.dto;

import com.EventPlanner.model.Account;
import com.EventPlanner.model.Contact;
import com.EventPlanner.model.EventType;
import com.EventPlanner.model.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VenueDto {
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private String website;
    private Double capacity;
    private String twitter;
    private String instagram;
    private String linkedin;
    private String attachments;
    private String googleMapLink;

    @NotBlank(message = "Primary contact name cannot be blank")
    private String primaryContactName;

    @NotBlank(message = "Primary contact phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Primary Contact Phone must be 10 digits")
    private String primaryContactPhone;

    @NotBlank(message = "Primary contact email cannot be blank")
    @Email(message = "Invalid email address")
    private String primaryContactEmail;

    private Boolean status;

    @NotNull(message = "Location cannot be null")
    private Location location;

    @NotNull(message = "Event type cannot be null")
    private EventType eventType;

    @NotNull(message = "Account cannot be null")
    private Account account;

    private Set<Contact> contacts;
}
