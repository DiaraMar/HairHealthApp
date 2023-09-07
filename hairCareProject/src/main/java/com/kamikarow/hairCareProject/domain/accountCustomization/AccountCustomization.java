package com.kamikarow.hairCareProject.domain.accountCustomization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.AccountCustomizationResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_customization")
public class AccountCustomization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id")
    @JsonIgnore
    private User owner;

    private boolean newsletter;
    private boolean sms;
    public AccountCustomization(User owner) {
        this.newsletter=true;
        this.sms=true;
        this.owner = owner;
    }


    public AccountCustomization update(Optional<AccountCustomization> accountCustomizationInDatabase, AccountCustomizationResponse accountCustomizationResponse) {
        return AccountCustomization
                .builder()
                .id(accountCustomizationInDatabase.get().id)
                .owner(accountCustomizationInDatabase.get().owner)
                .newsletter(accountCustomizationResponse.isNewsletter())
                .sms(accountCustomizationResponse.isSms())
                .build();
    }
}
