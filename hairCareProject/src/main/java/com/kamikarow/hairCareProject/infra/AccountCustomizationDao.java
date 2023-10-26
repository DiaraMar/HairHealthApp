package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import jakarta.transaction.Transactional;
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

    public Optional<AccountCustomization> getAccountCustomizationResponse(Long id) throws Exception {
        try{
            return getAccountCustomization(id);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public Optional<AccountCustomization> getAccountCustomization(Long id) throws Exception {
        try{
            return accountCustomizationJpaRepository.findAccountCustomization(id);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void updateSmsPreference(Long id, boolean sms){
        accountCustomizationJpaRepository.updateSmsPreference(id, sms);
    }

    @Transactional
    public void updateNewsLetterPreference(Long id, boolean newsLetter){
        accountCustomizationJpaRepository.updatenewsLetterPreference(id, newsLetter);
    }

}
