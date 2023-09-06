package com.kamikarow.hairCareProject.infra;

import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.exposition.DTO.UserDTO;
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
        System.out.println("debbug infra user" + user);
        return userJpaRepository.save(user);
    }

    public Optional<UserDTO> getUserProfil(String email){
        Optional<User> user = findByEmail(email);
        return Optional.ofNullable(new UserDTO().toUserDTO(user));

    }

    public Long getUserId(String email){
        Optional<User> user = findByEmail(email);
        return user.get().getId();

    }


}
