package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomizationInterface;
import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import com.kamikarow.hairCareProject.infra.AccountCustomizationDao;
import com.kamikarow.hairCareProject.infra.UserDao;
import com.kamikarow.hairCareProject.utility.exception.Unauthorized;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountCustomizationService implements AccountCustomizationInterface {

    private final AccountCustomizationDao accountCustomizationDao;
    private final UserDao userDao;

    private final JwtService jwtService;



    @Override
    public Optional<AccountCustomization> getAccountCustomization( String token) throws Exception {

        if(token ==null || token.isEmpty())
            throw new Unauthorized("You are not authorized to perform this action");


        Long id= getUserId(token);

        return getAccountCustomization(id);
    }

    @Override
    public AccountCustomization updateAccountCustomization(String token, AccountCustomization accountCustomizationResponse) throws Exception {

        Long userId = getUserId(token);
        Optional<AccountCustomization> accountCustomizationInDatabase = getAccountCustomization(userId);

        if(!accountCustomizationInDatabase.get().isSms()==accountCustomizationResponse.isSms()){
            updateSmsPreference(userId, accountCustomizationResponse.isSms());
            accountCustomizationInDatabase.get().setSms(accountCustomizationResponse.isSms());
        }
        if(!accountCustomizationInDatabase.get().isNewsletter()==accountCustomizationResponse.isNewsletter()){
            updateNewsLetterPreference(userId, accountCustomizationResponse.isNewsletter());
            accountCustomizationInDatabase.get().setNewsletter(accountCustomizationResponse.isNewsletter());
        }
        AccountCustomization accountCustomization = new AccountCustomization().toAccountCustomization(accountCustomizationInDatabase);

        return accountCustomization;
    }


    /****         Utils methods         **/
    private Long getUserId (String token){
        String email = getEmail(token);
        return userDao.findBy(email).get().getId();
    }
    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

    private Optional<AccountCustomization> getAccountCustomization(Long id) throws Exception {
        return accountCustomizationDao.getAccountCustomization(id);
    }

    private void updateSmsPreference(Long userId, boolean isSms){
        accountCustomizationDao.updateSmsPreference(userId, isSms);
    }

    private void updateNewsLetterPreference(Long userId, boolean isNewsletter){
        accountCustomizationDao.updateNewsLetterPreference(userId, isNewsletter);
    }


}
