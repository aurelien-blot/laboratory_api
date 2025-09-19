package com.castruche.laboratory_api.myworld_api.controller;

import com.castruche.laboratory_api.myworld_api.dto.contact.ContactDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserContactDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ConstantMyWorldUrl.CONTACT)
public class MWContactController {

    private final ContactService contactService;

    public MWContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping()
    public ResponseEntity<List<ContactDto>> getContactsForConnectedUser() {
        try {
            return ResponseEntity.ok(contactService.getContactsForConnectedUser());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/invite")
    public ResponseEntity<ContactDto> inviteContact(@RequestBody String contactUserName) {
        try {
            return ResponseEntity.ok(contactService.inviteContact(contactUserName));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search/new/{userName}")
    public ResponseEntity<UserContactDto> searchUserByUsername(@PathVariable String userName) {
        try {
            UserContactDto result = contactService.searchNewContactUserByUsername(userName);
            if(result!=null){
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
