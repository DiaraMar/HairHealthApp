package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.exposition.DTO.*;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.domain.auth.AuthInterface;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.infra.AccountCustomizationDao;
import com.kamikarow.hairCareProject.infra.UserDao;
import com.kamikarow.hairCareProject.utility.exception.Unauthorized;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthInterface {

    private final UserDao userDao;
    private final AccountCustomizationDao accountCustomizationDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {

  /**
   * if(!userDao.findByEmail(registerRequest.getEmail()).isEmpty() || userDao.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Username already in database");} replaced by @column(unique(true)*/
        var user = new RegisterRequest().toUser(registerRequest, encodePassword(registerRequest.getPassword()));
        user = save(user);

        var accountCustomization = new AccountCustomization(user);
        save(accountCustomization);

        var jwtToken = generateToken(user);
        return buildToken(jwtToken);
    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        var user = findBy(authenticationRequest.getEmail())
                .orElseThrow();

        var jwtToken = generateToken(user);
        return buildToken(jwtToken);
    }

    public AuthenticationResponse resetPassword(ResetPasswordRequest resetPasswordRequest, String token) {

        if(token == null || token.isEmpty() || resetPasswordRequest.getOldPassword()==null || resetPasswordRequest.getOldPassword().isEmpty() || resetPasswordRequest.getNewPassword()==null  || resetPasswordRequest.getNewPassword().isEmpty()){
            throw new Unauthorized("Request unauthorized");
        }
        String email = extractEmail(token);

        if(resetPasswordRequest.getEmail()!=null && !resetPasswordRequest.getEmail().isEmpty()){
            if(!resetPasswordRequest.getEmail().equalsIgnoreCase(email)){
                throw new Unauthorized("Request unauthorized");
            }
        }
        if(authenticate(new UsernamePasswordAuthenticationToken(email, resetPasswordRequest.getOldPassword())).isAuthenticated()){
            resetPasswordRequest.setEmail(email);
        }


        User user = new ResetPasswordRequest().toUser(findBy(email), encodePassword(resetPasswordRequest.getNewPassword()), email);
        resetPassword(user.getId(), user.getPassword());

        var jwtToken = generateToken(user);
        return buildToken(jwtToken);

    }





    private AuthenticationResponse buildToken(String token){
        return AuthenticationResponse.builder().token(token).build();
    }





    /****         Utils methods         **/
    private User save (User user){
        return userDao.save(user);
    }
    private void  resetPassword(Long id, String password){
        userDao.resetPassword(id, password);
    }
    private Optional<User> findBy(String email){
        return userDao.findBy(email);
    }

    private void save(AccountCustomization accountCustomization){
        accountCustomizationDao.save(accountCustomization);
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
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    }

}
