package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCustomizationResponse {
    private boolean newsletter;
    private boolean sms;

    public AccountCustomizationResponse toAccountCustomizationResponse(Optional<AccountCustomization> optionalAccountCustomization){
        var accountCustomizationResponse = optionalAccountCustomization.get();
        return AccountCustomizationResponse.builder()
                .sms(accountCustomizationResponse.isSms())
                .newsletter(accountCustomizationResponse.isNewsletter())
                .build();
    }


    public AccountCustomizationResponse toAccountCustomizationResponse(AccountCustomization accountCustomization){
        return AccountCustomizationResponse.builder()
                .sms(accountCustomization.isSms())
                .newsletter(accountCustomization.isNewsletter())
                .build();
    }



}
