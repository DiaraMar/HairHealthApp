package com.kamikarow.hairCareProject.domain.user;

import com.kamikarow.hairCareProject.exposition.DTO.UserDTO;

import java.util.Optional;

public interface UserInterface {

    public Optional<UserDTO> getUserProfil(String token);
    public UserDTO updateUserProfil(String token, UserDTO userDTO);
}
