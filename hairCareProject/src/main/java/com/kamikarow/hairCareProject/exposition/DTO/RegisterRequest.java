package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.domain.user.Role;
import com.kamikarow.hairCareProject.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    private AccountCustomization accountCustomization;




    public User toUser(RegisterRequest registerRequest, String passwordEncoded){

        return   User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoded)
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(Role.USER)
                .build();
    }

}