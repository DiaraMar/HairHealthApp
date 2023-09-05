package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDao {
   private final UserJpaRepository userJpaRepository;

    public Optional<User> findByEmail(String email){
      return  userJpaRepository.findByEmail(email);
    }

    public User save(User user){
      return userJpaRepository.save(user);
    }


}
