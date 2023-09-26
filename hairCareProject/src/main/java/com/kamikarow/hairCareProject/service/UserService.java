package com.kamikarow.hairCareProject.service;

import com.kamikarow.hairCareProject.exposition.config.JwtService;
import com.kamikarow.hairCareProject.domain.user.User;
import com.kamikarow.hairCareProject.domain.user.UserInterface;
import com.kamikarow.hairCareProject.exposition.DTO.UserDTO;
import com.kamikarow.hairCareProject.infra.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserInterface {

    private final UserDao userDao;
    private final JwtService jwtService;


    @Override
    public Optional<UserDTO> getUserProfil(String token) {
        String email = getEmail(token);
        return userDao.getUserProfil(email);
    }

    @Override
    public UserDTO updateUserProfil(String token, UserDTO userDTO) {

        System.out.println("debbug service userDto " + userDTO);

        String email = getEmail(token);

        //get userInDatabase
        Optional<User> userInDatabase = userDao.findByEmail(email);
        //toUser
        User updateUser = new UserDTO().toUser(userInDatabase,userDTO);
        System.out.println("debbug service updateUser " + updateUser);

        //saveUser

        Optional<User> updatedUser = Optional.ofNullable(userDao.save(updateUser));
        System.out.println("debbug service updatedUser" + updatedUser);

        //returnDto

        return new UserDTO().toUserDTO(updatedUser);
    }

    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

}
