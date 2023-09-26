package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.domain.auth.AuthInterface;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationRequest;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.RegisterRequest;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.ResetPasswordRequest;
import com.kamikarow.hairCareProject.infra.AccountCustomizationDao;
import com.kamikarow.hairCareProject.infra.UserDao;
import com.kamikarow.hairCareProject.utility.exception.Unauthorized;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        user = userDao.save(user);

        var accountCustomization = new AccountCustomization(user);
        accountCustomizationDao.save(accountCustomization);

        var jwtToken = generateToken(user);
        return buildToken(jwtToken);
    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        var user = userDao.findByEmail(authenticationRequest.getEmail())
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
        if(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, resetPasswordRequest.getOldPassword())).isAuthenticated()){
            resetPasswordRequest.setEmail(email);
        }


        User user = new ResetPasswordRequest().toUser(userDao.findByEmail(email), encodePassword(resetPasswordRequest.getNewPassword()), email);
        user = userDao.save(user);

        var jwtToken = generateToken(user);
        return buildToken(jwtToken);

    }

    private   String extractEmail(String token){
        return this.jwtService.extractUsername(token);
    }
    private String generateToken(User user){
        return  jwtService.generateToken(user);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    private AuthenticationResponse buildToken(String token){
        return AuthenticationResponse.builder().token(token).build();
    }


}
