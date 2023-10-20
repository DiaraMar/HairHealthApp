package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User,Long> {

    Optional<User>findByEmail(String email);



    @Modifying
    @Query(value = "UPDATE kkarowdb._user SET firstname = :firstname WHERE id = :id", nativeQuery = true)
    void updateFirstname(@Param("id") Long id, @Param("firstname") String firstname);

    @Modifying
    @Query(value = "UPDATE kkarowdb._user SET lastname = :lastname WHERE id = :id", nativeQuery = true)
    void updateLastname(@Param("id") Long id, @Param("lastname") String lastname);

    @Modifying
    @Query(value = "UPDATE kkarowdb._user SET email = :email WHERE id = :id", nativeQuery = true)
    void updateEmail(@Param("id") Long id, @Param("email") String email);

    @Modifying
    @Query(value = "UPDATE kkarowdb._user SET phone_number = :phone_number WHERE id = :id", nativeQuery = true)
    void updatePhoneNumber(@Param("id") Long id, @Param("phone_number") String phoneNumber);

    @Modifying
    @Query(value = "UPDATE kkarowdb._user SET password = :newPassword WHERE id = :id", nativeQuery = true)
    void updatePassword(@Param("id") Long id, @Param("newPassword") String newPassword);






}
