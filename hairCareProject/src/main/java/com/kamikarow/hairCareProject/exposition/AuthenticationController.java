package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationRequest;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.RegisterRequest;
import com.kamikarow.hairCareProject.service.AuthenticationService;
import com.kamikarow.hairCareProject.utility.exception.EmailAlreadyExistsException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kamikarow.hairCareProject.utility.exception.AuthenticationException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try{
            return ResponseEntity.ok(new AuthenticationResponse().toAuthenticationResponse(authenticationService.register(registerRequest.toUser())));
        }catch (EmailAlreadyExistsException emailAlreadyExistsException){
            throw new EmailAlreadyExistsException("The username is already register");
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        try{
            return ResponseEntity.ok(new AuthenticationResponse().toAuthenticationResponse(authenticationService.authenticate(authenticationRequest.toUser())));  }
        catch (AuthenticationException authenticationException){
            throw new AuthenticationException("The username or the password is wrong");
        }
    }
}