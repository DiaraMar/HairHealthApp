package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.user.Role;
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
        var user = optionalUser.get();
        return User.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .password(newPasswordEncoded)
                .email(emailFromToken)
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }



}
