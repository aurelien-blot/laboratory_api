package com.castruche.laboratory_api.main_api.service.util;

import com.castruche.laboratory_api.main_api.dao.util.SettingRepository;
import com.castruche.laboratory_api.main_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.laboratory_api.main_api.entity.util.Setting;
import com.castruche.laboratory_api.main_api.service.util.SecurityService;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SettingServiceTest {

    SettingService service;

    @Mock
    SettingRepository repository;

    @BeforeEach
    void setup() {
        service = new SettingService(repository);
    }


    @Test
    void getSettingValueByShortName_shouldReturnValue_whenSettingExists() {
        String shortName = "shortName";
        String value = "value";
        Setting entity = new Setting();
        entity.setId(1L);
        entity.setShortName(shortName);
        entity.setValue(value);
        when(repository.findByShortName(shortName)).thenReturn(entity);
        String result = service.getSettingValueByShortName(shortName);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(value);
        verify(repository).findByShortName(shortName);
    }

    @Test
    void getSettingValueByShortName_shouldReturnNull_whenSettingDoesNotExist() {
        String shortName = "shortName";
        when(repository.findByShortName(shortName)).thenReturn(null);
        String result = service.getSettingValueByShortName(shortName);
        assertThat(result).isNull();
    }
}
