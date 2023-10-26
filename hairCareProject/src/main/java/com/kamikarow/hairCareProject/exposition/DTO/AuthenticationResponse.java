package com.kamikarow.hairCareProject.exposition.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;


    public AuthenticationResponse toAuthenticationResponse(String token){
        return AuthenticationResponse.builder().token(token).build();
    }

}
