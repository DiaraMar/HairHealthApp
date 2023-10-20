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

        if(!userInDatabase.get().getFirstname().equals(updateUser.getFirstname()) && !updateUser.getFirstname().isEmpty()){
            userDao.updateFirstname(userInDatabase.get().getId(), updateUser.getFirstname());
            userInDatabase.get().setFirstname(updateUser.getFirstname());
        }

        if(!userInDatabase.get().getLastname().equals(updateUser.getLastname()) && !updateUser.getLastname().isEmpty()){
            userDao.updateLastname(userInDatabase.get().getId(), updateUser.getLastname());
            userInDatabase.get().setLastname(updateUser.getLastname());
        }

        //todo : fix. update works one time only
        if(!userInDatabase.get().getEmail().equals(updateUser.getEmail()) && !updateUser.getEmail().isEmpty()) {
            userDao.updateEmail(userInDatabase.get().getId(), updateUser.getEmail());
            userInDatabase.get().setEmail(updateUser.getEmail());
        }

        if(!userInDatabase.get().getPhoneNumber().equals(updateUser.getPhoneNumber()) && !updateUser.getPhoneNumber().isEmpty()) {
            userDao.updatePhoneNumber(userInDatabase.get().getId(), updateUser.getPhoneNumber());
            userInDatabase.get().setPhoneNumber(updateUser.getPhoneNumber());
        }

        return new UserDTO().toUserDTO(userInDatabase);
    }

    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

}
