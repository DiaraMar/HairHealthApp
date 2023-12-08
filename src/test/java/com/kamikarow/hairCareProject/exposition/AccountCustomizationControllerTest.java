package com.kamikarow.hairCareProject.exposition;

import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import com.kamikarow.hairCareProject.service.AccountCustomizationService;
import com.kamikarow.hairCareProject.utility.BearerTokenWrapper;
import com.kamikarow.hairCareProject.utility.exception.RessourceNotFoundException;
import com.kamikarow.hairCareProject.utility.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AccountCustomizationControllerTest {
    @Mock
    private AccountCustomizationService accountCustomizationService;

    @Mock
    private BearerTokenWrapper tokenWrapper;

    @InjectMocks
    private AccountCustomizationController accountCustomizationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAccountCustomization_Success() throws Exception {
        when(tokenWrapper.getToken()).thenReturn("dummyToken");
        when(accountCustomizationService.getAccountCustomization(anyString())).thenReturn(Optional.empty());
        ResponseEntity<Optional<AccountCustomizationResponse>> responseEntity = accountCustomizationController.getAccountCustomization();
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void updateAccount_Success() throws Exception {
        when(tokenWrapper.getToken()).thenReturn("dummyToken");
        when(accountCustomizationService.updateAccountCustomization(anyString(), any())).thenReturn(new AccountCustomization());
        AccountCustomizationResponse accountCustomizationResponse = new AccountCustomizationResponse();
        ResponseEntity<Optional<AccountCustomizationResponse>> responseEntity = accountCustomizationController.updateAccount(accountCustomizationResponse);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    @Test
    void getAccountCustomization_UnauthorizedExceptionThrown() throws Exception {
        when(tokenWrapper.getToken()).thenReturn("");
        assertThrows(UnauthorizedException.class, () -> accountCustomizationController.getAccountCustomization());
    }

    @Test
    void getAccountCustomization_ResourceNotFoundExceptionThrown() throws Exception {
        when(tokenWrapper.getToken()).thenReturn("dummyToken");
        when(accountCustomizationService.getAccountCustomization(anyString())).thenThrow(new RessourceNotFoundException("Resource not found"));
        assertThrows(RessourceNotFoundException.class, () -> accountCustomizationController.getAccountCustomization());
    }

    @Test
    void updateAccount_UnauthorizedExceptionThrown() throws Exception {
        when(tokenWrapper.getToken()).thenReturn("");
        AccountCustomizationResponse accountCustomizationResponse = new AccountCustomizationResponse();
        assertThrows(UnauthorizedException.class, () -> accountCustomizationController.updateAccount(accountCustomizationResponse));
    }

    @Test
    void updateAccount_ResourceNotFoundExceptionThrown() throws Exception {
        when(tokenWrapper.getToken()).thenReturn("dummyToken");
        when(accountCustomizationService.updateAccountCustomization(anyString(), any())).thenThrow(new RessourceNotFoundException("Resource not found"));
        AccountCustomizationResponse accountCustomizationResponse = new AccountCustomizationResponse();
        assertThrows(RessourceNotFoundException.class, () -> accountCustomizationController.updateAccount(accountCustomizationResponse));
    }

}