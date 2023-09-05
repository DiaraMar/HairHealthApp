package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.config.JwtService;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomizationInterface;
import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationRequest;
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
    public Optional<AccountCustomizationResponse> getAccountCustomization( String token) throws Exception {

        if(token ==null || token.isEmpty())
            throw new Unauthorized("You are not authorized to perform this action");


        Long id= getUserId(token);

        return accountCustomizationDao.getAccountCustomizationResponse(id);
    }

    @Override
    public AccountCustomizationResponse updateAccountCustomization(String token, AccountCustomizationResponse accountCustomizationResponse) throws Exception {
        Long userId = getUserId(token);
        Optional<AccountCustomization> accountCustomizationInDatabase = accountCustomizationDao.getAccountCustomization(userId);
        AccountCustomization accountCustomization = new AccountCustomization().update(accountCustomizationInDatabase,accountCustomizationResponse);
        return new AccountCustomizationResponse().toAccountCustomizationResponse(accountCustomizationDao.save(accountCustomization));
    }

    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

    private Long getUserId (String token){
        String email = getEmail(token);
        return userDao.findByEmail(email).get().getId();
    }
}
