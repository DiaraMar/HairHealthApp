package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountCustomizationJpaRepository  extends JpaRepository<AccountCustomization,Long> {

    @Query(value="select * from kkarowdb.account_customization a WHERE a.owner = :id",  nativeQuery = true)
    Optional<AccountCustomization> findAccountCustomizationBy(@Param("id") Long userID);

    @Modifying
    @Query(value = "UPDATE kkarowdb.account_customization SET sms = :sms WHERE id = :id", nativeQuery = true)
    void updateSmsPreference(@Param("id") Long id, @Param("sms") boolean sms);

    @Modifying
    @Query(value = "UPDATE kkarowdb.account_customization SET newsletter = :newsletter WHERE id = :id", nativeQuery = true)
    void updatenewsLetterPreference(@Param("id") Long id, @Param("newsletter") boolean newsletter);


}
