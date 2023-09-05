package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountCustomizationDao {

    private final AccountCustomizationJpaRepository accountCustomizationJpaRepository;

    public AccountCustomization save(AccountCustomization accountCustomization){
        return accountCustomizationJpaRepository.save(accountCustomization);
    }

    public Optional<AccountCustomizationResponse> getAccountCustomizationResponse(Long id) throws Exception {
        try{
            return Optional.ofNullable(new AccountCustomizationResponse().toAccountCustomizationResponse(accountCustomizationJpaRepository.findAccountCustomizationBy(id)));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public Optional<AccountCustomization> getAccountCustomization(Long id) throws Exception {
        try{
            return accountCustomizationJpaRepository.findAccountCustomizationBy(id);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
