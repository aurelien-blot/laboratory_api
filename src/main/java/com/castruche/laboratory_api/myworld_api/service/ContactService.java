package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.main_api.dto.util.ConnectedUserDto;
import com.castruche.laboratory_api.main_api.service.GenericService;
import com.castruche.laboratory_api.myworld_api.dao.ContactRepository;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.dto.contact.ContactDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserContactDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;
import com.castruche.laboratory_api.myworld_api.entity.Contact;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.enums.ContactStatus;
import com.castruche.laboratory_api.myworld_api.formatter.ContactFormatter;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService extends GenericService<Contact, ContactDto, ContactDto> {

    private static final Logger logger = LogManager.getLogger(ContactService.class);

    private final ContactRepository contactRepository;
    private final ContactFormatter contactFormatter;

    private final ConnectedUserService connectedUserService;
    private final UserService userService;

    public ContactService(ContactRepository contactRepository,
                          ContactFormatter contactFormatter,ConnectedUserService connectedUserService,
                          UserService userService){
        super(contactRepository, contactFormatter);
        this.contactRepository = contactRepository;
        this.contactFormatter = contactFormatter;
        this.connectedUserService = connectedUserService;
        this.userService = userService;
    }

    @Transactional
    public List<ContactDto> getContactsForConnectedUser(){
        User connectedUser = connectedUserService.getCurrentUserEntity();
        if(connectedUser == null){
            throw new RuntimeException("User not found");
        }
        List<Contact> contacts = contactRepository.findByUserId(connectedUser.getId());
        return contactFormatter.entityToDto(contacts);
    }

    @Transactional
    public ContactDto inviteContact(String contactUserName){
        User connectedUser = connectedUserService.getCurrentUserEntity();
        User contactUser = userService.selectByUsername(contactUserName);
        if(contactUser == null){
            throw new RuntimeException("User not found");
        }
        Contact contact = contactRepository.findByUserIdAndContactId(connectedUser.getId(), contactUser.getId());
        if(contact != null){
            throw new RuntimeException("Contact already exists");
        }
        contact = new Contact();
        contact.setUser(connectedUser);
        contact.setContact(contactUser);
        contact.setContactStatus(ContactStatus.PENDING);
        contact = contactRepository.save(contact);
        return contactFormatter.entityToDto(contact);
    }

    @Transactional
    public UserContactDto searchNewContactUserByUsername(String userName){
        //TODO TEST
        User connectedUser = connectedUserService.getCurrentUserEntity();
        //Si le user est le meme que le connecté on retourne null
        if(connectedUser.getUsername().equals(userName)){
            return null;
        }
        User contactUser = userService.selectByUsername(userName);
        //Si le user n'est pas trouvé on retourne null
        if(contactUser == null){
            return null;
        }
        Contact contact = contactRepository.findByUserIdAndContactId(connectedUser.getId(), contactUser.getId());
        //Si le contact existe déjà on retourne null
        if(contact != null){
            return null;
        }
        return contactFormatter.entityToContactDto(contactUser);
    }


}
