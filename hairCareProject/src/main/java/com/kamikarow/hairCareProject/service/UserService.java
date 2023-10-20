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
        return getProfil(email);
    }

    @Override
    public UserDTO updateUserProfil(String token, UserDTO userDTO) {

        System.out.println("debbug service userDto " + userDTO);

        String email = getEmail(token);

        //get userInDatabase
        Optional<User> userInDatabase = findBy(email);
        //toUser
        User updateUser = new UserDTO().toUser(userInDatabase,userDTO);
        System.out.println("debbug service updateUser " + updateUser);

        //saveUser

        if(!userInDatabase.get().getFirstname().equals(updateUser.getFirstname()) && !updateUser.getFirstname().isEmpty()){
            updateFirstname(userInDatabase.get().getId(), updateUser.getFirstname());
            userInDatabase.get().setFirstname(updateUser.getFirstname());
        }

        if(!userInDatabase.get().getLastname().equals(updateUser.getLastname()) && !updateUser.getLastname().isEmpty()){
            updateLastname(userInDatabase.get().getId(), updateUser.getLastname());
            userInDatabase.get().setLastname(updateUser.getLastname());
        }

        //todo : fix. update works one time only
        if(!userInDatabase.get().getEmail().equals(updateUser.getEmail()) && !updateUser.getEmail().isEmpty()) {
            updateEmail(userInDatabase.get().getId(), updateUser.getEmail());
            userInDatabase.get().setEmail(updateUser.getEmail());
        }

        if(!userInDatabase.get().getPhoneNumber().equals(updateUser.getPhoneNumber()) && !updateUser.getPhoneNumber().isEmpty()) {
            updatePhoneNumber(userInDatabase.get().getId(), updateUser.getPhoneNumber());
            userInDatabase.get().setPhoneNumber(updateUser.getPhoneNumber());
        }

        return new UserDTO().toUserDTO(userInDatabase);
    }

    /****         Utils methods         **/

    private Optional<User> findBy(String email){
        return userDao.findBy(email);
    }
    private Optional<UserDTO> getProfil(String email){
        return userDao.getUserProfil(email);
    }
    private void updateFirstname(Long id, String firstname){
        userDao.updateFirstname(id, firstname);
    }
    private void updateLastname(Long id, String lastname){
        userDao.updateLastname(id, lastname);
    }
    private void updateEmail(Long id, String email){
        userDao.updateEmail(id, email);
    }
    private void updatePhoneNumber(Long id, String firstname){
        userDao.updateFirstname(id, firstname);
    }
    private  String getEmail (String token){
        return this.jwtService.extractUsername(token);
    }

}
