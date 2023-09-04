package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;


    public User toUser(Optional<User> optionalUser, String newPasswordEncoded, String emailFromToken){
        return User.builder()
                .id(optionalUser.get().getId())
                .firstname(optionalUser.get().getFirstname())
                .lastname(optionalUser.get().getLastname())
                .password(newPasswordEncoded)
                .email(emailFromToken)
                .phoneNumber(optionalUser.get().getPhoneNumber())
                .role(optionalUser.get().getRole())
                .build();

    }
}
