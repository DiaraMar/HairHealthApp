package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationRequest;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.RegisterRequest;
import com.kamikarow.hairCareProject.service.AuthenticationService;
import com.kamikarow.hairCareProject.utility.exception.ConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest("Dia", "Mar", "dm@gmail.com", "12345", "0664716511", null);
        when(authenticationService.register(any(User.class))).thenReturn("TOKEN"); // Mocking the service method call
        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);
        assertEquals("TOKEN", response.getBody().getToken()); // Verify if the token matches the expected value
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("dm@gmail.com", "12345");
        when(authenticationService.authenticate(any(User.class))).thenReturn("TOKEN"); // Mocking the service method call

        ResponseEntity<AuthenticationResponse> response = authenticationController.authenticate(authenticationRequest);

        assertEquals("TOKEN", response.getBody().getToken()); // Verify if the token matches the expected value
    }

    @Test
    void testRegisterResponseStatus() {
        RegisterRequest registerRequest = new RegisterRequest("Dia", "Mar", "dm@gmail.com", "12345", "0664716511", null);
        when(authenticationService.register(any(User.class))).thenReturn("TOKEN");

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAuthenticateResponseStatus() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("dm@gmail.com", "12345");
        when(authenticationService.authenticate(any(User.class))).thenReturn("TOKEN");

        ResponseEntity<AuthenticationResponse> response = authenticationController.authenticate(authenticationRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void testRegisterConflictException() {
        RegisterRequest registerRequest = new RegisterRequest("Dia", "Mar", "dm@gmail.com", "12345", "0664716511", null);
        when(authenticationService.register(any(User.class)))
                .thenThrow(new ConflictException("REGISTER IS NOT POSSIBLE"));

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testAuthenticateConflictException() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("dm@gmail.com", "12345");
        when(authenticationService.authenticate(any(User.class)))
                .thenThrow(new ConflictException("AUTHENTICATION FAILED"));
        ResponseEntity<AuthenticationResponse> response = authenticationController.authenticate(authenticationRequest);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }}

