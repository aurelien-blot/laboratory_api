package com.castruche.laboratory_api.myworld_api.entity;

import com.castruche.laboratory_api.main_api.entity.AbstractEntity;
import com.castruche.laboratory_api.myworld_api.enums.ContactStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Contact extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private ContactStatus contactStatus;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private User contact;

    public ContactStatus getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(ContactStatus contactStatus) {
        this.contactStatus = contactStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }
}
