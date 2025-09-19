package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConnectedUserServiceTest {

    ConnectedUserService service;

    @Mock
    UserRepository userRepository;

    @Mock
    UserFormatter userFormatter;

    @BeforeEach
    void setup() {
        service = new ConnectedUserService(userRepository, userFormatter);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentUserEntity_shouldReturnNull_whenAuthenficationFail() {
        SecurityContextHolder.clearContext();
        User response = service.getCurrentUserEntity();
        assertThat(response).isNull();
        verifyNoInteractions(userRepository);
    }

    @Test
    void getCurrentUserEntity_shouldReturnUser_whenAuthenficationSucceed() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setUsername("test");
        user.setId(1L);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        User response = service.getCurrentUserEntity();
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("test");
        assertThat(response.getId()).isEqualTo(1L);
        verify(userRepository, times(1)).findByUsername(anyString());

    }

}
