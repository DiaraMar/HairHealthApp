package com.kamikarow.hairCareProject.exposition.DTO;

import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import com.kamikarow.hairCareProject.domain.diagnostic.Diagnostic;
import com.kamikarow.hairCareProject.domain.routine.Routine;
import com.kamikarow.hairCareProject.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteUserResponse {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;


    private AccountCustomization accountCustomization;

    private List<Diagnostic> diagnostics;

    private List<Routine> routines;
    public CompleteUserResponse toCompleteUserResponseDTO(Optional<User> user){
        return CompleteUserResponse.builder()
                .firstname(user.get().getFirstname())
                .lastname(user.get().getLastname())
                .email(user.get().getEmail())
                .phoneNumber(user.get().getPhoneNumber())
                .accountCustomization(user.get().getAccountCustomization())
                .diagnostics(user.get().getDiagnostics())
                .routines(user.get().getRoutines())
                .build();
    }

    public User toUser(){
        return   User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .phoneNumber(phoneNumber)
                .accountCustomization(accountCustomization)
                .diagnostics(diagnostics)
                .routines(routines)
                .build();
    }


}
