package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.myworld_api.dao.PictureRepository;
import com.castruche.laboratory_api.myworld_api.dao.PostRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PictureServiceTest {

    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private PictureService pictureService;

}
