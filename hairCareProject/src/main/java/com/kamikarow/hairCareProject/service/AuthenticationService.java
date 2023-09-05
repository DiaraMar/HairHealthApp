package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.config.JwtService;
import com.kamikarow.hairCareProject.domain.user.UserInterface;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationRequest;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.RegisterRequest;
import com.kamikarow.hairCareProject.domain.user.Role;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.ResetPasswordRequest;
import com.kamikarow.hairCareProject.infra.UserDao;
import com.kamikarow.hairCareProject.utility.exception.EmailAlreadyExistsException;
import com.kamikarow.hairCareProject.utility.exception.Unauthorized;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserInterface {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if(!userDao.findByEmail(registerRequest.getEmail()).isEmpty() || userDao.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Username already in database");
        }

        var user = new RegisterRequest().toUser(registerRequest, passwordEncoder.encode(registerRequest.getPassword()));
        user = userDao.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        var user = userDao.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse resetPassword(ResetPasswordRequest resetPasswordRequest, String token) {

        if(token == null || token.isEmpty() || resetPasswordRequest.getOldPassword()==null || resetPasswordRequest.getOldPassword().isEmpty() || resetPasswordRequest.getNewPassword()==null  || resetPasswordRequest.getNewPassword().isEmpty()){
            throw new Unauthorized("Request unauthorized");
        }
        String email = getEmail(token);

        if(resetPasswordRequest.getEmail()!=null && !resetPasswordRequest.getEmail().isEmpty()){
            if(!resetPasswordRequest.getEmail().equalsIgnoreCase(email)){
                throw new Unauthorized("Request unauthorized");
            }
        }
        if(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, resetPasswordRequest.getOldPassword())).isAuthenticated()){
            resetPasswordRequest.setEmail(email);
        }


        User user = new ResetPasswordRequest().toUser(userDao.findByEmail(email), passwordEncoder.encode(resetPasswordRequest.getNewPassword()), email);
        user = userDao.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }
}
