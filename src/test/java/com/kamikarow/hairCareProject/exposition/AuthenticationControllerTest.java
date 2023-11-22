package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.RegisterRequest;
import com.kamikarow.hairCareProject.service.AuthenticationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfControllerReturnsExpectedType() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("marmelade@gmail.com");
        registerRequest.setFirstname("marie");
        registerRequest.setLastname("malade");
        registerRequest.setPhoneNumber("+3365341222201");
        registerRequest.setPassword("azerty");

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(registerRequest);

        assertTrue(responseEntity.getClass().equals(ResponseEntity.class));
        assertTrue(responseEntity.getBody() instanceof AuthenticationResponse);
    }

    @Test
    public final void registerReturn200() {
        RegisterRequest registerRequest = new RegisterRequest();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(registerRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testRegisterWithNullInput() {
        RegisterRequest registerRequest = null;
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(registerRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testRegisterWithNullAuthenticationResponse() {
        RegisterRequest registerRequest = new RegisterRequest();
        when(authenticationService.register(registerRequest)).thenReturn(null);
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(registerRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}