package com.kamikarow.hairCareProject.exposition;


import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationRequest;
import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import com.kamikarow.hairCareProject.service.AccountCustomizationService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
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

    @GetMapping
    public ResponseEntity<Optional<AccountCustomizationResponse>> getAccountCustomization() throws Exception {
        try{
            String token = getToken ();
            return ResponseEntity.ok(this.accountCustomizationService.getAccountCustomization( token));
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    @PatchMapping
    //todo : force filled RequestBody value in front-end
    public ResponseEntity<Optional<AccountCustomizationResponse>> updateAccount(@RequestBody AccountCustomizationResponse accountCustomizationResponse) throws Exception {
        try{
            String token = getToken ();
            return ResponseEntity.ok(Optional.ofNullable(this.accountCustomizationService.updateAccountCustomization(token, accountCustomizationResponse)));
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
