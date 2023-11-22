package com.kamikarow.hairCareProject.domain.user;
import java.util.Optional;

public interface UserInterface {

    public Optional<User> getUserProfil(String token);
    public User updateUserProfil(String token, User user);
}
