package com.castruche.laboratory_api.main_api.service.util;

import com.castruche.laboratory_api.main_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.main_api.service.util.SecurityService;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {

    SecurityService service;

    @Mock
    UserRepository repository;

    @BeforeEach
    void setup() {
        service = new SecurityService(repository);
    }


    @Test
    void checkPassword_shouldReturnDtoFalse_whenPasswordIsInCorrect() {
        User entity = new User(1L, "Test");
        entity.setPassword(service.encodePassword("password"));
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        BooleanResponseDto response = service.checkPassword("incorrectPassword", entity);
        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isFalse();

        User entityUpdated = repository.findById(1L).get();
        assertThat(entityUpdated.getTentatives()).isEqualTo(1);
    }

    @Test
    void checkPassword_shouldReturnDtoTrue_whenPasswordIsCorrect() {
        User entity = new User(2L, "Test");
        entity.setTentatives(2);
        entity.setPassword(service.encodePassword("password"));
        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        BooleanResponseDto response = service.checkPassword("password", entity);
        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isTrue();

        User entityUpdated = repository.findById(2L).get();
        assertThat(entityUpdated.getTentatives()).isEqualTo(0);
    }

    @Test
    void comparePassword_shouldReturnDtoFalse_whenPasswordIsIncorrect(){
        BooleanResponseDto response = service.comparePassword("test", "test2", "error");
        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isFalse();
        assertThat(response.getMessage()).isEqualTo("error");
    }

    @Test
    void comparePassword_shouldReturnDtoTrue_whenPasswordIsCorrect(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("test");
        String password = "test";
        BooleanResponseDto response = service.comparePassword(password, encodedPassword, "error");
        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isTrue();
    }
}
