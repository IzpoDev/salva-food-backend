package com.salvafood.api.salvafood_api.controller;

import com.salvafood.api.salvafood_api.model.dto.*;
import com.salvafood.api.salvafood_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto userRequest) {
        UserResponseDto createdUser = userService.createUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updatedUser(@RequestBody @Valid UserRequestDto userRequest, @PathVariable("id") Long id) {
        UserResponseDto userResponseDto = userService.updateUser(userRequest, id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Long id) {
        UserResponseDto userResponseDto = userService.getUserById(id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("El usuario con el id: " + id + " fue eliminado correctamente ", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.authUser(loginRequestDto);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.ACCEPTED);
    }

    //Endpoints admin
    @PostMapping("/create-admin")
    public ResponseEntity<UserResponseDto> createAdminUser(@RequestBody @Valid UserRequestDto userRequest) {
        UserResponseDto createdUser = userService.createUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //Endpoints for password reset
    @PostMapping("/start-reset-password/{email}")
    public ResponseEntity<String> startResetPassword(@PathVariable("email") String email) {
        String response = userService.startForgotPassword(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordDto resetPasswordRequest) {
        String response = userService.resetPassword(resetPasswordRequest);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}