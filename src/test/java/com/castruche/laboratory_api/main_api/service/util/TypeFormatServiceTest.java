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



}
