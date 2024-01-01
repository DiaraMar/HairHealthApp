package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationRequest;
import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.CompleteUserRequest;
import com.kamikarow.hairCareProject.exposition.DTO.RegisterRequest;
import com.kamikarow.hairCareProject.service.AuthenticationService;
import com.kamikarow.hairCareProject.utility.exception.ConflictException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kamikarow.hairCareProject.utility.exception.AccessRightsException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try{
            return ResponseEntity.ok(new AuthenticationResponse().toAuthenticationResponse(register(registerRequest.toUser())));
        }catch (ConflictException conflictException){
            throw conflictException;
        } catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws AccessRightsException {
        try{
            return ResponseEntity.ok(new AuthenticationResponse().toAuthenticationResponse(authenticate(authenticationRequest.toUser())));  }
        catch (ConflictException conflictException){
            throw conflictException;
        } catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/pilote/delete") //todo admin
    public ResponseEntity deleteUser( @RequestBody CompleteUserRequest completeUserRequest) throws AccessRightsException {
        try{
            this.authenticationService.delete(completeUserRequest.getEmail());
            return (ResponseEntity) ResponseEntity.ok( );
        }
        catch (ConflictException conflictException){
            throw conflictException;
        } catch (Exception e){
            throw e;
        }
    }

    /** Utils Methods **/



    private  String authenticate(User authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }
    private String register(User register) throws ConflictException {
        return authenticationService.register(register);
    }
    private void delete(String email) throws ConflictException {
        authenticationService.delete(email);
    }

}