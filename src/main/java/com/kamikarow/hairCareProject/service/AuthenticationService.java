package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.exposition.DTO.*;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.domain.auth.AuthInterface;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.utility.exception.BadRequestException;
import com.kamikarow.hairCareProject.utility.exception.ConflictException;
import com.kamikarow.hairCareProject.utility.exception.InternalServerError;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  AuthenticationService implements AuthInterface {

    private final UserService userService;
    private final AccountCustomizationService accountCustomizationService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(User registerRequest) {

        var user = new RegisterRequest().formatUser(registerRequest, encodePassword(registerRequest.getPassword()));
        if(!findBy(user.getEmail()).isEmpty()){
            throw new ConflictException("REGISTER IS NOT POSSIBLE");
        }
        user = save(user);
        save(new AccountCustomization(user));

        return generateToken(user);
    }
    public String authenticate(User authenticationRequest) {
        authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        var user = findBy(authenticationRequest.getEmail());
        if(user.isEmpty()){
            throw new ConflictException("AUTHENTICATION FAILED");
        }
        return generateToken(user.get());
    }
    public AuthenticationResponse resetPassword(ResetPasswordRequest resetPasswordRequest, String token) {

        if(token == null || token.isEmpty() || resetPasswordRequest.getOldPassword()==null || resetPasswordRequest.getOldPassword().isEmpty() || resetPasswordRequest.getNewPassword()==null  || resetPasswordRequest.getNewPassword().isEmpty()){
            throw new BadRequestException("RESET PASSWORD FAILED");
        }
        String email = extractEmail(token);

       if(!authenticate(new UsernamePasswordAuthenticationToken(email, resetPasswordRequest.getOldPassword())).isAuthenticated()) {
           throw new UnauthorizedException("RESET PASSWORD UNAUTHORIZED");
       }

        User user = new ResetPasswordRequest().toUser(findBy(email), encodePassword(resetPasswordRequest.getNewPassword()), email);
        resetPassword(user.getId(), user.getPassword());

        var jwtToken = generateToken(user);

        if(jwtToken.isEmpty()){
            throw new InternalServerError("SERVER ERROR  GENERATE TOKEN");
        }
        return buildToken(jwtToken);
    }

    /****         Utils methods         **/

    private AuthenticationResponse buildToken(String token){
        return AuthenticationResponse.builder().token(token).build();
    }
    private User save (User user){
        return userService.save(user);
    }
    private void  resetPassword(Long id, String password){
        userService.resetPassword(id, password);
    }
    private Optional<User> findBy(String email){
        return userService.findBy(email);
    }

    private void save(AccountCustomization accountCustomization){
        accountCustomizationService.save(accountCustomization);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
    private   String extractEmail(String token){
        return this.jwtService.extractUsername(token);
    }
    private String generateToken(User user){
        return  jwtService.generateToken(user);
    }

    private Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken); //Overide default exception raised
    }

}
