package com.salvafood.api.salvafood_api.service;

import com.salvafood.api.salvafood_api.model.dto.LoginResponseDto;
import com.salvafood.api.salvafood_api.model.dto.LoginRequestDto;
import com.salvafood.api.salvafood_api.model.dto.UserRequestDto;
import com.salvafood.api.salvafood_api.model.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public UserResponseDto createUser(UserRequestDto userRequest);
    public UserResponseDto updateUser(UserRequestDto userRequestDto, Long id);
    public List<UserResponseDto> getAllUsers();
    public UserResponseDto getUserById(Long id);
    public void deleteUserById(Long id);

    //Funciones de autenticacion
    public LoginResponseDto authUser(LoginRequestDto loginRequestDto);
}
