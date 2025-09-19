package com.castruche.laboratory_api.myworld_api.formatter;

import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.myworld_api.dto.contact.ContactDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserContactDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.entity.Contact;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.service.ContactService;
import com.castruche.laboratory_api.myworld_api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactFormatter implements IFormatter<Contact, ContactDto, ContactDto> {

    private final UserFormatter userFormatter;
    private final UserService userService;

    public ContactFormatter(UserFormatter userFormatter, UserService userService) {
        this.userFormatter = userFormatter;
        this.userService = userService;
    }

    @Override
    public ContactDto entityToDto(Contact entity) {
        if(entity == null){
            return null;
        }
        ContactDto dto = new ContactDto();
        dto.setId(entity.getId());
        dto.setCreationTime(entity.getCreationTime());
        dto.setModificationTime(entity.getModificationTime());
        UserContactDto user = entityToContactDto(entity.getUser());
        UserContactDto contact = entityToContactDto(entity.getContact());
        dto.setUser(user);
        dto.setContact(contact);
        dto.setContactStatus(entity.getContactStatus());
        return dto;
    }

    @Override
    public ContactDto entityToLightDto(Contact contact) {
       return null;
    }

    @Override
    public Contact dtoToEntity(ContactDto dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        return updateEntityFromDto(contact, dto);
    }

    @Override
    public List<ContactDto> entityToLightDto(List<Contact> list) {
        return IFormatter.super.entityToLightDto(list);
    }

    @Override
    public List<ContactDto> entityToDto(List<Contact> list) {
        return IFormatter.super.entityToDto(list);
    }

    @Override
    public Contact updateEntityFromDto(Contact entity, ContactDto dto) {
        entity.setContactStatus(dto.getContactStatus());
        if(null!=entity.getUser() && null!=entity.getUser().getId()){
            entity.setUser(userService.selectById(entity.getUser().getId()));
        }
        if(null!=entity.getContact() && null!=entity.getContact().getId()){
            entity.setContact(userService.selectById(entity.getContact().getId()));
        }

        return entity;
    }

    public UserContactDto entityToContactDto(User entity) {
        if(entity == null){
            return null;
        }
        UserContactDto userDto = new UserContactDto();
        userDto.setId(entity.getId());
        userDto.setUsername(entity.getUsername());
        return userDto;
    }
}
