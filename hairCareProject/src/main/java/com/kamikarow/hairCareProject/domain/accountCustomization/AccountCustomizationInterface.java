package com.kamikarow.hairCareProject.domain.accountCustomization;


import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import java.util.Optional;

public interface AccountCustomizationInterface {

    public Optional<AccountCustomization> getAccountCustomization( String token) throws Exception;
    public AccountCustomization updateAccountCustomization(String token, AccountCustomization AccountCustomization) throws Exception;
}
