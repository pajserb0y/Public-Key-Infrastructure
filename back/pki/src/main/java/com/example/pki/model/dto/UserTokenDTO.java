package com.example.pki.model.dto;

import com.example.pki.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String jwtToken;
}
