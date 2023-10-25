package com.kamikarow.hairCareProject.domain.accountCustomization;


import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import java.util.Optional;

public interface AccountCustomizationInterface {

    public Optional<AccountCustomizationResponse> getAccountCustomization( String token) throws Exception;
    public AccountCustomizationResponse updateAccountCustomization(String token, AccountCustomizationResponse AccountCustomizationResponse) throws Exception;
}
