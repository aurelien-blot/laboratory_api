package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.main_api.configuration.JwtUtil;
import com.castruche.laboratory_api.main_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.laboratory_api.main_api.service.util.TypeFormatService;
import com.castruche.laboratory_api.myworld_api.dao.PictureRepository;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import com.castruche.laboratory_api.myworld_api.service.util.MWMailService;
import com.castruche.laboratory_api.myworld_api.service.util.MWSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MWLoginServiceTest {

    MWLoginService service;

    @Mock
    UserRepository userRepository;

    @Mock
    UserFormatter userFormatter;

    @Mock
    UserService userService;

    @Mock
    JwtUtil jwtTokenUtil;

    @Mock
    MWMailService mailService;

    @Mock
    MWSecurityService securityService;

    @Mock
    TypeFormatService typeFormatService;

    @BeforeEach
    void setup() {
        service = new MWLoginService(userRepository, userFormatter, userService, jwtTokenUtil, mailService, securityService, typeFormatService);
    }

    @Test
    void checkUsernameAvailability_ReturnsTrue_WhenUsernameIsAvailable() {
        when(userRepository.existsByUsername("Test")).thenReturn(false);
        BooleanResponseDto response = service.checkUsernameAvailability("Test");
        assertThat(response.isStatus()).isTrue();
    }

    @Test
    void checkUsernameAvailability_ReturnsFalse_WhenUsernameIsNotAvailable() {
        when(userRepository.existsByUsername("Test")).thenReturn(true);
        BooleanResponseDto response = service.checkUsernameAvailability("Test");
        assertThat(response.isStatus()).isFalse();
    }

    @Test
    void checkMailAvailability_ReturnsTrue_WhenMailIsAvailable() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        BooleanResponseDto response = service.checkMailAvailability("test@test.com");
        assertThat(response.isStatus()).isTrue();
    }

    @Test
    void checkMailAvailability_ReturnsFalse_WhenMailIsNotAvailable() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);
        BooleanResponseDto response = service.checkMailAvailability("test@test.com");
        assertThat(response.isStatus()).isFalse();
    }

    @Test
    void register_shouldReturnDto_WhenUserIsRegistered() {
        //UserDto userDto = new UserDto(1L, "test");
        //TODO FINIR TEST
    }

    @Test
    void register_shouldThrowException_WhenUserIsNotRegistered() {
        //TODO FINIR TEST
    }

    @Test
    void login_shouldReturnTrue_WhenUserIsLoggedIn() {
        //TODO FINIR TEST
    }

    @Test
    void login_shouldFalse_WhenUserIsNotLoggedIn() {
        //TODO FINIR TEST
    }

    @Test
    void verifyMail_shouldReturnTrue_WhenMailIsVerified() {
        //TODO FINIR TEST
    }

    @Test
    void verifyMail_shouldReturnFalse_WhenMailIsNotVerified() {
        //TODO FINIR TEST
    }

    @Test
    void sendResetPasswordMail_shouldReturnTrue_WhenMailIsSent() {
        //TODO FINIR TEST
    }

    @Test
    void sendResetPasswordMail_shouldReturnFalse_WhenMailIsNotSent() {
        //TODO FINIR TEST
    }

    @Test
    void resetPassword_shouldReturnTrue_WhenPasswordIsReset() {
        //TODO FINIR TEST
    }

    @Test
    void resetPassword_shouldReturnFalse_WhenPasswordIsNotReset() {
        //TODO FINIR TEST
    }
}
