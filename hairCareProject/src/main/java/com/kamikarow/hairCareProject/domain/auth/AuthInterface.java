package com.kamikarow.hairCareProject.domain.auth;

import com.kamikarow.hairCareProject.domain.user.User;


public interface AuthInterface {

    public String register(User registerRequest);
    public String authenticate(User authenticationRequest);

    //updateAccount
}
