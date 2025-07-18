package com.salvafood.api.salvafood_api.mapper;

import com.salvafood.api.salvafood_api.model.dto.UserRequestDto;
import com.salvafood.api.salvafood_api.model.dto.UserResponseDto;
import com.salvafood.api.salvafood_api.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public static UserEntity toEntity(UserRequestDto userRequestDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequestDto.getName());
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setPassword(userRequestDto.getPassword());
        userEntity.setPhoneNumber(userRequestDto.getPhoneNumber());
        return userEntity;
    }
    public static UserResponseDto toDto(UserEntity userEntity) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userEntity.getId());
        userResponseDto.setName(userEntity.getName());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setPhoneNumber(userEntity.getPhoneNumber());
        return userResponseDto;
    }
    public static List<UserResponseDto> toListDto(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(UserMapper::toDto)
                .toList();
    }
}
