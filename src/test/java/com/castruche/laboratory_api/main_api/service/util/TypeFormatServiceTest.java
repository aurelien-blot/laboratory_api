package com.castruche.laboratory_api.main_api.service.util;

import com.castruche.laboratory_api.main_api.dao.util.SettingRepository;
import com.castruche.laboratory_api.main_api.entity.util.Setting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TypeFormatServiceTest {

    TypeFormatService service;


    @BeforeEach
    void setup() {
        service = new TypeFormatService();
    }


    @Test
    void isMail_shouldReturnTrue_whenMailIsCorrect() {
        String mail = "test@test.com";
        boolean result = service.isMail(mail);
        assertThat(result).isTrue();
    }

    @Test
    void isMail_shouldReturnFalse_whenMailIsIncorrect() {
        String mail = "testtest.com";
        boolean result = service.isMail(mail);
        assertThat(result).isFalse();
        mail = "test@test";
        result = service.isMail(mail);
        assertThat(result).isFalse();
        mail = "test@test.c";
        result = service.isMail(mail);
        assertThat(result).isFalse();
    }

    @Test
    void isAlpha_shouldReturnTrue_whenStringIsAlpha() {
        String str = "test";
        boolean result = service.isAlpha(str);
        assertThat(result).isTrue();
    }

    @Test
    void isAlpha_shouldReturnFalse_whenStringIsNotAlpha() {
        String str = "test1";
        boolean result = service.isAlpha(str);
        assertThat(result).isFalse();
    }

    @Test
    void isAlphaNumeric_shouldReturnTrue_whenStringIsAlphaNumeric() {
        String str = "test1";
        boolean result = service.isAlphaNumeric(str);
        assertThat(result).isTrue();

        str="1121";
        result=service.isAlphaNumeric(str);
        assertThat(result).isTrue();

        str="tst";
        result=service.isAlphaNumeric(str);
        assertThat(result).isTrue();
    }

    @Test
    void isAlphaNumeric_shouldReturnFalse_whenStringIsNotAlphaNumeric() {
        String str = "test1_";
        boolean result = service.isAlphaNumeric(str);
        assertThat(result).isFalse();
    }
}
