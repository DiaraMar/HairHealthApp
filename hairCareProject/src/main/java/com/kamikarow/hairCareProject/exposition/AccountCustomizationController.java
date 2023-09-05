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

        String token = tokenWrapper.getToken();
            return ResponseEntity.ok(this.accountCustomizationService.getAccountCustomization( token));
    }

    @PatchMapping
    public ResponseEntity<Optional<AccountCustomizationResponse>> getAccountCustomization(@RequestBody AccountCustomizationResponse accountCustomizationResponse) throws Exception {
        String token = tokenWrapper.getToken();
        return ResponseEntity.ok(Optional.ofNullable(this.accountCustomizationService.updateAccountCustomization(token, accountCustomizationResponse)));
    }

}
