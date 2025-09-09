package com.castruche.laboratory_api.myworld_api.formatter;

import com.castruche.laboratory_api.main_api.dto.util.ConnectedUserDto;
import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFormatter implements IFormatter<User, UserDto, UserLightDto> {

    @Override
    public UserDto entityToDto(User entity) {
        if(entity == null){
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setUsername(entity.getUsername());
        userDto.setEmail(entity.getEmail());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setMailVerified(entity.isMailVerified());
        return userDto;
    }

    @Override
    public UserLightDto entityToLightDto(User user) {
        if(user==null){
            return null;
        }
        UserLightDto userLightDto = new UserLightDto();
        userLightDto.setId(user.getId());
        userLightDto.setUsername(user.getUsername());
        return userLightDto;
    }

    @Override
    public User dtoToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());

        return updateEntityFromDto(user, dto);
    }

    @Override
    public List<UserLightDto> entityToLightDto(List<User> list) {
        return IFormatter.super.entityToLightDto(list);
    }

    @Override
    public List<UserDto> entityToDto(List<User> list) {
        return IFormatter.super.entityToDto(list);
    }

    public ConnectedUserDto entityToConnectedUserDto(User entity) {
        if(entity == null){
            return null;
        }
        ConnectedUserDto dto = new ConnectedUserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Override
    public User updateEntityFromDto(User entity, UserDto dto) {
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return entity;
    }
}
