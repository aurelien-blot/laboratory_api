package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.myworld_api.dao.ContactRepository;
import com.castruche.laboratory_api.myworld_api.dao.PostRepository;
import com.castruche.laboratory_api.myworld_api.dto.contact.ContactDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserContactDto;
import com.castruche.laboratory_api.myworld_api.entity.Contact;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.enums.ContactStatus;
import com.castruche.laboratory_api.myworld_api.formatter.ContactFormatter;
import com.castruche.laboratory_api.myworld_api.formatter.PostFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    private ContactService service;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private ContactFormatter contactFormatter;

    @Mock
    private ConnectedUserService connectedUserService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        service = new ContactService(contactRepository, contactFormatter,
                connectedUserService, userService);
    }

    @Test
    void getContactsForConnectedUser_ShouldThrowException_WhenUserConnectedIsNull() {
        when(connectedUserService.getCurrentUserEntity()).thenReturn(null);

        Assertions.assertThrows(RuntimeException.class, () -> {
            service.getContactsForConnectedUser();
        });
    }

    @Test
    void getContactsForConnectedUser_ShouldReturnContacts_WhenUserConnectedIsNotNull() {
        User userConnected = new User();
        userConnected.setId(1L);
        userConnected.setUsername("test");

        User userContact = new User();
        userContact.setId(2L);
        userContact.setUsername("test2");

        List<Contact> contacts = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setUser(userConnected);
        contact1.setContact(userContact);
        contact1.setContactStatus(ContactStatus.PENDING);
        contacts.add(contact1);

        UserContactDto userDto = new UserContactDto();
        userDto.setId(userConnected.getId());
        userDto.setUsername(userConnected.getUsername());

        UserContactDto userContactDto = new UserContactDto();
        userContactDto.setId(userContact.getId());
        userContactDto.setUsername(userContact.getUsername());


        List<ContactDto> contactDtos = new ArrayList<>();
        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact1.getId());
        contactDto.setUser(userDto);
        contactDto.setContact(userContactDto);
        contactDto.setContactStatus(contact1.getContactStatus());
        contactDtos.add(contactDto);

        when(connectedUserService.getCurrentUserEntity()).thenReturn(userConnected);
        when(contactRepository.findByUserId(userConnected.getId())).thenReturn(contacts);
        when(contactFormatter.entityToDto(contacts)).thenReturn(contactDtos);

        List<ContactDto> result = service.getContactsForConnectedUser();

        Assertions.assertEquals(contactDtos, result);
    }

    @Test
    void inviteContact_ShouldThrowException_WhenContactAlreadyExists() {
        User connectedUser = new User(1L, "user");
        User contactUser = new User(2L, "contact");
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setUser(connectedUser);
        contact.setContact(contactUser);
        contact.setContactStatus(ContactStatus.PENDING);
        when(contactRepository.findByUserIdAndContactId(connectedUser.getId(), contactUser.getId())).thenReturn(contact);
        when(connectedUserService.getCurrentUserEntity()).thenReturn(connectedUser);
        when(userService.selectByUsername("contact")).thenReturn(contactUser);

        Assertions.assertThrows(RuntimeException.class, () -> {
            service.inviteContact("contact");
        });
    }

    @Test
    void inviteContact_ShouldReturnContactDto_WhenContactDoesNotExist() {
        User connectedUser = new User(1L, "user");
        User contactUser = new User(2L, "contact");
        Contact contact = new Contact();
        contact.setUser(connectedUser);
        contact.setContact(contactUser);
        contact.setContactStatus(ContactStatus.PENDING);
        when(connectedUserService.getCurrentUserEntity()).thenReturn(connectedUser);
        when(contactRepository.findByUserIdAndContactId(connectedUser.getId(), contactUser.getId())).thenReturn(null);
        when(userService.selectByUsername("contact")).thenReturn(contactUser);
        when(contactRepository.save(any(Contact.class))).thenAnswer(invocation -> {
            Contact c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        ContactDto expected = new ContactDto();
        expected.setId(1L);
        expected.setUser(new UserContactDto(1L, "user"));
        expected.setContact(new UserContactDto(2L, "contact"));
        expected.setContactStatus(ContactStatus.PENDING);
        when(contactFormatter.entityToDto(any(Contact.class))).thenReturn(expected);

        ContactDto result = service.inviteContact("contact");
        assertThat(result).isEqualTo(expected);
        verify(connectedUserService).getCurrentUserEntity();
        verify(userService).selectByUsername("contact");
        verify(contactRepository).findByUserIdAndContactId(1L, 2L);
        verify(contactRepository).save(any(Contact.class));
        verify(contactFormatter).entityToDto(any(Contact.class));
        verifyNoMoreInteractions(connectedUserService, userService, contactRepository, contactFormatter);
    }

    @Test
    void searchNewContactUserByUsername_ShouldReturnNull_WhenUserConnectedIsTheSame() {
        User connectedUser = new User(1L, "user");
        when(connectedUserService.getCurrentUserEntity()).thenReturn(connectedUser);

        UserContactDto result = service.searchNewContactUserByUsername("user");

        assertThat(result).isNull();
        verify(connectedUserService).getCurrentUserEntity();
        verifyNoMoreInteractions(connectedUserService, userService);
    }

    @Test
    void searchNewContactUserByUsername_ShouldReturnNull_WhenUserIsNotFound() {
        User connectedUser = new User(1L, "user");
        when(connectedUserService.getCurrentUserEntity()).thenReturn(connectedUser);
        when(userService.selectByUsername("test")).thenReturn(null);
        UserContactDto result = service.searchNewContactUserByUsername("test");

        assertThat(result).isNull();
        verify(connectedUserService).getCurrentUserEntity();
        verify(userService).selectByUsername("test");
        verifyNoMoreInteractions(connectedUserService, userService);
    }

    @Test
    void searchNewContactUserByUsername_ShouldReturnNull_WhenContactAlreadyExists() {
        User connectedUser = new User(1L, "user");
        User contactUser = new User(2L, "contact");
        Contact contact = new Contact();
        contact.setUser(connectedUser);
        contact.setContact(contactUser);
        contact.setContactStatus(ContactStatus.PENDING);
        when(connectedUserService.getCurrentUserEntity()).thenReturn(connectedUser);
        when(userService.selectByUsername("contact")).thenReturn(contactUser);
        when(contactRepository.findByUserIdAndContactId(connectedUser.getId(), contactUser.getId())).thenReturn(contact);

        UserContactDto result = service.searchNewContactUserByUsername("contact");

        assertThat(result).isNull();
        verify(connectedUserService).getCurrentUserEntity();
        verify(userService).selectByUsername("contact");
        verify(contactRepository).findByUserIdAndContactId(1L, 2L);
        verifyNoMoreInteractions(connectedUserService, userService, contactRepository);
    }

    @Test
    void searchNewContactUserByUsername_ShouldReturnUserContactDto_WhenUserIsFoundAndContactDoesNotExist() {
        User connectedUser = new User(1L, "user");
        User contactUser = new User(2L, "contact");
        Contact contact = new Contact();
        contact.setUser(connectedUser);
        contact.setContact(contactUser);
        contact.setContactStatus(ContactStatus.PENDING);

        UserContactDto expected = new UserContactDto();
        expected.setId(contactUser.getId());
        expected.setUsername(contactUser.getUsername());

        when(connectedUserService.getCurrentUserEntity()).thenReturn(connectedUser);
        when(userService.selectByUsername("contact")).thenReturn(contactUser);
        when(contactRepository.findByUserIdAndContactId(connectedUser.getId(), contactUser.getId())).thenReturn(null);
        when(contactFormatter.entityToContactDto(any(User.class))).thenReturn(expected);
        UserContactDto result = service.searchNewContactUserByUsername("contact");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(contactUser.getId());
        verify(connectedUserService).getCurrentUserEntity();
        verify(userService).selectByUsername("contact");
        verify(contactRepository).findByUserIdAndContactId(1L, 2L);
        verifyNoMoreInteractions(connectedUserService, userService, contactRepository);
    }
}
