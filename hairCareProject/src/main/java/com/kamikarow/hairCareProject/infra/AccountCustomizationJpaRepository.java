package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.accountCustomization.AccountCustomization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountCustomizationJpaRepository  extends JpaRepository<AccountCustomization,Long> {

    @Query(value="select * from kkarowdb.account_customization a WHERE a.owner = :id",  nativeQuery = true)
    Optional<AccountCustomization> findAccountCustomizationBy(@Param("id") Long userID);


}
