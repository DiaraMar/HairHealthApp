package com.kamikarow.hairCareProject.exposition;


import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import com.kamikarow.hairCareProject.service.AccountCustomizationService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accountExperience")
@RequiredArgsConstructor
public class AccountCustomizationController {

    private static final Logger logger = LogManager.getLogger(AccountCustomizationController.class);

    private final AccountCustomizationService accountCustomizationService;
    private final BearerTokenWrapper tokenWrapper;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<Optional<AccountCustomizationResponse>> getAccountCustomization() throws Exception {
        try{
            String token = getToken ();
            return ResponseEntity.ok(Optional.ofNullable(new AccountCustomizationResponse().toAccountCustomizationResponse(this.accountCustomizationService.getAccountCustomization(token))));
        }catch(Exception e){
            throw new Exception(e);
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PatchMapping
    public ResponseEntity<Optional<AccountCustomizationResponse>> updateAccount(@RequestBody AccountCustomizationResponse accountCustomizationResponse) throws Exception {
        try{
            String token = getToken ();
            return ResponseEntity.ok(Optional.ofNullable(new AccountCustomizationResponse().toAccountCustomizationResponse(this.accountCustomizationService.updateAccountCustomization(token, accountCustomizationResponse.toAccountCustomization()))));
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    private String getToken () throws Exception {
        try{
            return tokenWrapper.getToken();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}
