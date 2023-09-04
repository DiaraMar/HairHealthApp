package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;


    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setFirstname(this.firstname);
        user.setLastname(this.lastname);
        user.setPassword(this.password);
        user.setPhoneNumber(this.phoneNumber);
        return user;
    }
}