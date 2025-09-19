package com.castruche.laboratory_api.myworld_api.dto.contact;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserContactDto;
import com.castruche.laboratory_api.myworld_api.enums.ContactStatus;

public class ContactDto extends AbstractDto {
    private UserContactDto user;
    private UserContactDto contact;
    private ContactStatus contactStatus;

    public UserContactDto getUser() {
        return user;
    }

    public void setUser(UserContactDto user) {
        this.user = user;
    }

    public UserContactDto getContact() {
        return contact;
    }

    public void setContact(UserContactDto contact) {
        this.contact = contact;
    }

    public ContactStatus getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(ContactStatus contactStatus) {
        this.contactStatus = contactStatus;
    }
}
