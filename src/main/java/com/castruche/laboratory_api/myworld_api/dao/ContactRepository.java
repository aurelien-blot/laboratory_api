package com.castruche.laboratory_api.myworld_api.dao;

import com.castruche.laboratory_api.myworld_api.entity.Contact;
import com.castruche.laboratory_api.myworld_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByUserId(Long userId);
    Contact findByUserIdAndContactId(Long userId, Long contactId);
}
