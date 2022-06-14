package com.example.pki.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\.])[A-Za-z\\d][A-Za-z\\d!@#$%^&*()_+\\.]{8,20}$")
    private String password;
}
