package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.ResetPasswordRequest;
import com.kamikarow.hairCareProject.exposition.DTO.UserResponse;
import com.kamikarow.hairCareProject.service.AuthenticationService;
import com.kamikarow.hairCareProject.service.LogoutService;
import com.kamikarow.hairCareProject.service.UserService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import com.kamikarow.hairCareProject.utility.exception.BadRequestException;
import com.kamikarow.hairCareProject.utility.exception.ConflictException;
import com.kamikarow.hairCareProject.utility.exception.InternalServerError;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import jakarta.validation.Valid;
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
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final BearerTokenWrapper tokenWrapper;
    private final LogoutService logoutService;


    @PatchMapping("/password/new")
    public ResponseEntity<AuthenticationResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        try{
            return ResponseEntity.ok(authenticationService.resetPassword(resetPasswordRequest, getToken()));
        }catch(BadRequestException | UnauthorizedException | InternalServerError exception){
            throw exception;
        }
        catch (ConflictException conflictException){
            throw conflictException;
        }
    }

    @PostMapping("/disconnection") //todo
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        logoutService.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping("/profil")
    public ResponseEntity<Optional<UserResponse>> getUserProfil() throws Exception {
        try{
            return ResponseEntity.ok(Optional.ofNullable(new UserResponse().toUserDTO(userService.getUserProfil(getToken()))));
        }catch(UnauthorizedException unauthorizedException){
            throw unauthorizedException;
        }
        catch (Exception e){
            throw e;
        }
    }

    @PatchMapping("/profil")
    public ResponseEntity<UserResponse> updateUserProfil(@RequestBody UserResponse updatedProfil) throws Exception {
        try{
            return ResponseEntity.ok(new UserResponse().toUserDTO(userService.updateUserProfil(getToken(), updatedProfil.toUser())));
        }catch(UnauthorizedException unauthorizedException){
            throw unauthorizedException;
        }catch (Exception e){
            throw e;
        }
    }

    private String getToken () {
        String token =  tokenWrapper.getToken();

        if(token.isEmpty())
            throw new UnauthorizedException("");
        return token;
    }


}
