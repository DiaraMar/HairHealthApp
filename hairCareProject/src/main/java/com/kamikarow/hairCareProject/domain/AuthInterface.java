package com.kamikarow.hairCareProject.domain;

import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationRequest;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.RegisterRequest;

public interface AuthInterface {

    public AuthenticationResponse register(RegisterRequest registerRequest);
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    //updateAccount
}
