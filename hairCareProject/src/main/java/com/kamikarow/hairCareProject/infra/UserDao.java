package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.UserDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDao {
   private final UserJpaRepository userJpaRepository;

    public Optional<User> findBy(String email){
      return  userJpaRepository.findByEmail(email);
    }

    public User save(User user){
        System.out.println("debbug infra user" + user);
        return userJpaRepository.save(user);
    }


    public Optional<User> getUserProfil(String email){
        Optional<User> user = findBy(email);
        return user;

    }

    @Transactional
    public void updateFirstname(Long id, String firstname){
        userJpaRepository.updateFirstname(id, firstname);
    }

    @Transactional
    public void updateLastname(Long id, String lastname){
        userJpaRepository.updateLastname(id, lastname);
    }

    @Transactional
    public void updateEmail(Long id, String email){
        userJpaRepository.updateEmail(id, email);
    }

    @Transactional
    public void updatePhoneNumber(Long id, String phoneNumber){
        userJpaRepository.updatePhoneNumber(id, phoneNumber);
    }


    @Transactional
    public void resetPassword(Long id, String newPassword){
        System.out.println("debbug reset password");
        userJpaRepository.updatePassword(id, newPassword);
    }


}
