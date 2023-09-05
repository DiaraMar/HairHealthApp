package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.exposition.DTO.AuthenticationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.ResetPasswordRequest;
import com.kamikarow.hairCareProject.service.AuthenticationService;
import com.kamikarow.hairCareProject.service.LogoutService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import com.kamikarow.hairCareProject.utility.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class AccountController {

    private static final Logger logger = LogManager.getLogger(AccountController.class);

    private final AuthenticationService authenticationService;
    private final BearerTokenWrapper tokenWrapper;
    private final LogoutService logoutService;


    @PostMapping("/password/new")
    public ResponseEntity<AuthenticationResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try{
            return ResponseEntity.ok(authenticationService.resetPassword(resetPasswordRequest, tokenWrapper.getToken()));
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


}
