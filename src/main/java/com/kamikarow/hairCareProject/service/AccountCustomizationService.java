package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.CompleteAccountCustomizationRequest;
import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomizationInterface;
import com.kamikarow.hairCareProject.infra.AccountCustomizationDao;
import com.kamikarow.hairCareProject.utility.exception.RessourceNotFoundException;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountCustomizationService implements AccountCustomizationInterface {

    private final AccountCustomizationDao accountCustomizationDao;

    private final UserService userService;
    private final JwtService jwtService;



    @Override
    public Optional<AccountCustomization> getAccountCustomization( String token) throws Exception {
        if(token ==null || token.isEmpty())
            throw new UnauthorizedException("The client must authenticate itself to get the requested response");

        Long userId= getUserId(token);
        if(userId<1)
            throw new UnauthorizedException("The client must authenticate itself to get the requested response");

        return getAccountCustomization(userId);
    }

    @Override
    public AccountCustomization updateAccountCustomization(String token, AccountCustomization accountCustomizationResponse) throws Exception {

        if(token ==null || token.isEmpty() || getUserId(token)<1)
            throw new UnauthorizedException("Unauthorized");

        Long userId = getUserId(token);
        if(userId<1)
            throw new UnauthorizedException("The client must authenticate itself to get the requested response");

        Optional<AccountCustomization> accountCustomizationInDatabase = getAccountCustomization(userId);
        if(accountCustomizationInDatabase.isEmpty())
            throw new RessourceNotFoundException("Cannot find the requested resource"); //todo overide by this one

        if(!comparing(accountCustomizationInDatabase.get().isSms(),accountCustomizationResponse.isSms())){
            updateSmsPreference(userId, accountCustomizationResponse.isSms());
            accountCustomizationInDatabase.get().setSms(accountCustomizationResponse.isSms());
        }
        if(!comparing(accountCustomizationInDatabase.get().isNewsletter(),accountCustomizationResponse.isNewsletter())){
            updateNewsLetterPreference(userId, accountCustomizationResponse.isNewsletter());
            accountCustomizationInDatabase.get().setNewsletter(accountCustomizationResponse.isNewsletter());
        }

        return new AccountCustomization().toAccountCustomization(accountCustomizationInDatabase);
    }


    /****         Utils methods         **/

    private boolean comparing(boolean a, boolean b){
        return a==b;
    }

    private User getUser (String token){
        String email = getEmail(token);
        return getUserByEmail(email);
    }

    private User getUserByEmail(String email) {
        return userService.findBy(email).get();
    }


    private Long getUserId (String token){
        return getUser(token).getId();
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

    void save (AccountCustomization accountCustomization){
        accountCustomizationDao.save(accountCustomization);
    }

    public AccountCustomization updateAccountCustomization(CompleteAccountCustomizationRequest completeAccountCustomizationRequest) throws Exception {

        Long userId = getUserByEmail(completeAccountCustomizationRequest.getUsername()).getId();
        if(userId<1)
            throw new UnauthorizedException("The client does not exist");

        Optional<AccountCustomization> accountCustomizationInDatabase = getAccountCustomization(userId);
        if(accountCustomizationInDatabase.isEmpty())
            throw new RessourceNotFoundException("Cannot find the requested resource"); //todo overide by this one

        if(!comparing(accountCustomizationInDatabase.get().isSms(),completeAccountCustomizationRequest.isSms())){
            updateSmsPreference(userId, completeAccountCustomizationRequest.isSms());
            accountCustomizationInDatabase.get().setSms(completeAccountCustomizationRequest.isSms());
        }
        if(!comparing(accountCustomizationInDatabase.get().isNewsletter(),completeAccountCustomizationRequest.isNewsletter())){
            updateNewsLetterPreference(userId, completeAccountCustomizationRequest.isNewsletter());
            accountCustomizationInDatabase.get().setNewsletter(completeAccountCustomizationRequest.isNewsletter());
        }

        return new AccountCustomization().toAccountCustomization(accountCustomizationInDatabase);
    }
}
