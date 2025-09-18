package com.castruche.laboratory_api.myworld_api.service.util;

import com.castruche.laboratory_api.main_api.configuration.JwtUtil;
import com.castruche.laboratory_api.main_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.laboratory_api.main_api.enums.ResponseCodeEnum;
import com.castruche.laboratory_api.main_api.service.util.SecurityService;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MWSecurityServiceTest {

    MWSecurityService service;

    @Mock
    UserRepository repository;

    @Mock
    MWSettingService settingService;

    @Mock
    MWMailService mailService;

    @Mock
    UserFormatter userFormatter;

    @Mock
    JwtUtil jwtTokenUtil;

    @BeforeEach
    void setup() {
        service = new MWSecurityService(settingService, jwtTokenUtil, repository, mailService, userFormatter);
    }

    @Test
    void checkTentativesConnexions_shouldBlockAndReturnForbidden_whenAttemptsExceedMax() {
        // Arrange
        when(settingService.getTentativesBeforeBlocking()).thenReturn(3);

        User user = new User(1L, "Test");
        user.setTentatives(4);           // > max → bloque, pas d'email
        user.setBlocked(false);

        UserDto dto = new UserDto(1L, "Test");
        when(userFormatter.entityToDto(user)).thenReturn(dto);
        when(repository.save(user)).thenReturn(user);

        // Act
        BooleanResponseDto result = service.checkTentativesConnexions(user, new BooleanResponseDto());

        // Assert
        assertThat(result.isStatus()).isFalse();
        assertThat(result.getCode()).isEqualTo(ResponseCodeEnum.FORBIDDEN.getCode());
        assertThat(user.isBlocked()).isTrue();

        // Vérifie la séquence essentielle
        InOrder inOrder = inOrder(settingService, userFormatter, repository);
        inOrder.verify(settingService).getTentativesBeforeBlocking();
        inOrder.verify(userFormatter).entityToDto(user);
        inOrder.verify(repository).save(user);

        // Pas d’email ni de token quand tentatives > max
        verify(jwtTokenUtil, never()).generateToken(anyString());
        verify(mailService, never()).sendMailForPasswordReset(any(), anyString());
    }

    @Test
    void checkTentativesConnexions_shouldGenerateTokenAndSendMail_whenAttemptsEqualMax() {
        // Arrange
        when(settingService.getTentativesBeforeBlocking()).thenReturn(3);

        User user = new User(1L, "Test");
        user.setTentatives(3);           // == max → bloque + email
        user.setBlocked(false);

        UserDto dto = new UserDto(1L, "Test");
        when(userFormatter.entityToDto(user)).thenReturn(dto);
        when(jwtTokenUtil.generateToken("Test")).thenReturn("tok123");
        when(repository.save(user)).thenReturn(user);

        // Act
        BooleanResponseDto result = service.checkTentativesConnexions(user, new BooleanResponseDto());

        // Assert
        assertThat(result.isStatus()).isFalse();
        assertThat(result.getCode()).isEqualTo(ResponseCodeEnum.FORBIDDEN.getCode());
        assertThat(user.isBlocked()).isTrue();
        assertThat(user.getResetPasswordToken()).isEqualTo("tok123");

        InOrder inOrder = inOrder(settingService, userFormatter, jwtTokenUtil, mailService, repository);
        inOrder.verify(settingService).getTentativesBeforeBlocking();
        inOrder.verify(userFormatter).entityToDto(user);
        inOrder.verify(jwtTokenUtil).generateToken("Test");
        inOrder.verify(mailService).sendMailForPasswordReset(dto, "tok123");
        inOrder.verify(repository).save(user);
    }


}
