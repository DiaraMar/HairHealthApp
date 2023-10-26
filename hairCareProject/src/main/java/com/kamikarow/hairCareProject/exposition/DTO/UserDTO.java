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
public class UserDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;

    public UserDTO toUserDTO(Optional<User> optionalUser){
        var user = optionalUser.get();
        return UserDTO.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
    public UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public User toUser(){
      return   User.builder()
              .firstname(firstname)
              .lastname(lastname)
              .email(email)
              .phoneNumber(phoneNumber)
              .build();
    }
    public User toUser(Optional<User> optionalUser, User updatedUser){
        var user = optionalUser.get();
        var userFirstname = isNullOrEmpty(updatedUser.getFirstname())?user.getFirstname():updatedUser.getFirstname();
        var userLastname = isNullOrEmpty(updatedUser.getLastname())?user.getLastname():updatedUser.getLastname();
        var userEmail = isNullOrEmpty(updatedUser.getEmail())?user.getEmail():updatedUser.getEmail();
        var userPhoneNumber = isNullOrEmpty(updatedUser.getPhoneNumber())?user.getPhoneNumber():updatedUser.getPhoneNumber();

        return User.builder()
                .id(user.getId())
                .firstname(userFirstname)
                .lastname(userLastname)
                .email(userEmail)
                .phoneNumber(userPhoneNumber)
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    private boolean isNullOrEmpty(String input){
        if(input==null)
            return true;
        if (input==null){
            return true;
        }
        return false;
    }
}
