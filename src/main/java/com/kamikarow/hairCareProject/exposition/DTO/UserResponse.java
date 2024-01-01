package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;

    public UserResponse toUserDTO(Optional<User> optionalUser){
        var user = optionalUser.get();
        return UserResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
    public UserResponse toUserDTO(User user){
        return UserResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public List<Optional<UserResponse>> toUserResponseDtoList(List<User> users){
        List<Optional<UserResponse>> usersResponses = new ArrayList<>();
        for(User user : users){
            usersResponses.add(Optional.ofNullable(toUserDTO(user)));
       }
        return usersResponses;
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
