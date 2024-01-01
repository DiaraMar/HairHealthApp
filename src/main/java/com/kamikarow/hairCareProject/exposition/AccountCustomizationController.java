package com.kamikarow.hairCareProject.exposition;


import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import com.kamikarow.hairCareProject.exposition.DTO.CompleteAccountCustomizationRequest;
import com.kamikarow.hairCareProject.service.AccountCustomizationService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import com.kamikarow.hairCareProject.utility.exception.RessourceNotFoundException;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accountExperience")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AccountCustomizationController {

    //private static final Logger logger = LogManager.getLogger(AccountCustomizationController.class);

    private final AccountCustomizationService accountCustomizationService;
    private final BearerTokenWrapper tokenWrapper;

    @GetMapping
    public ResponseEntity<Optional<AccountCustomizationResponse>> getAccountCustomization() throws Exception {
        try{
            String token = getToken ();
            return ResponseEntity.ok(Optional.ofNullable(new AccountCustomizationResponse().toAccountCustomizationResponse(getAccountCustomization(token))));
        }catch(UnauthorizedException | RessourceNotFoundException e){
            throw e;
        } catch(Exception e){
            throw e;
        }
    }

    @PatchMapping
    public ResponseEntity<Optional<AccountCustomizationResponse>> updateAccount(@RequestBody AccountCustomizationResponse accountCustomizationResponse) throws Exception {
        try{
            String token = getToken ();
            return ResponseEntity.ok(Optional.ofNullable(new AccountCustomizationResponse().toAccountCustomizationResponse(updateAccountCustomization(token, accountCustomizationResponse.toAccountCustomization()))));
        }catch(UnauthorizedException | RessourceNotFoundException e){
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            throw e;
        } catch(Exception e){
            throw e;
        }
    }
    @PatchMapping("/pilote")
    public ResponseEntity<Optional<AccountCustomizationResponse>> updateAccountSuper(@RequestBody CompleteAccountCustomizationRequest completeAccountCustomizationRequest) throws Exception {
        try{
            return ResponseEntity.ok(Optional.ofNullable(new AccountCustomizationResponse().toAccountCustomizationResponse(updateAccountCustomization(completeAccountCustomizationRequest))));
        }catch(UnauthorizedException | RessourceNotFoundException e){
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            throw e;
        } catch(Exception e){
            throw e;
        }
    }

    /** Utils Methods **/

    private Optional<AccountCustomization> getAccountCustomization(String token) throws Exception {
        return this.accountCustomizationService.getAccountCustomization(token);
    }
    private AccountCustomization updateAccountCustomization(String token, AccountCustomization accountCustomizationResponse) throws Exception {
        return this.accountCustomizationService.updateAccountCustomization(token, accountCustomizationResponse);
    }
    private AccountCustomization updateAccountCustomization(CompleteAccountCustomizationRequest completeAccountCustomizationRequest) throws Exception {
        return this.accountCustomizationService.updateAccountCustomization(completeAccountCustomizationRequest);
    }

    private String getToken () {
            String token =  tokenWrapper.getToken();

            if(token.isEmpty())
                throw new UnauthorizedException("");
            return token;
    }
}
