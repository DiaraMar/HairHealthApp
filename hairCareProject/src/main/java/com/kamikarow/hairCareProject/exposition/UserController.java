package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.ResetPasswordRequest;
import com.kamikarow.hairCareProject.exposition.DTO.UserDTO;
import com.kamikarow.hairCareProject.service.AuthenticationService;
import com.kamikarow.hairCareProject.service.LogoutService;
import com.kamikarow.hairCareProject.service.UserService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import com.kamikarow.hairCareProject.utility.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final BearerTokenWrapper tokenWrapper;
    private final LogoutService logoutService;


    @PostMapping("/password/new")
    public ResponseEntity<AuthenticationResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try{
            return ResponseEntity.ok(authenticationService.resetPassword(resetPasswordRequest, getToken()));
        }catch (EmailAlreadyExistsException emailAlreadyExistsException){
            throw new EmailAlreadyExistsException("The username is already register");
        }
    }

    @PostMapping("/disconnection")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("debbug");
        System.out.println("HttpServletRequest request " +  request);
        System.out.println("HttpServletResponse response " +  response);
        logoutService.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping("/profil")
    public ResponseEntity<Optional<UserDTO>> getUserProfil() throws Exception {
        try{
            return ResponseEntity.ok(userService.getUserProfil(getToken()));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PatchMapping("/profil")
    public ResponseEntity<Optional<UserDTO>> updateUserProfil(@RequestBody UserDTO updatedProfil) throws Exception {
        System.out.println("debbug controller " + updatedProfil);
        try{
            return ResponseEntity.ok(Optional.ofNullable(userService.updateUserProfil(getToken(), updatedProfil)));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private String getToken(){
        return tokenWrapper.getToken();
    }



}
