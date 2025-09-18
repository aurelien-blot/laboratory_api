package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.myworld_api.dao.PostRepository;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.formatter.PictureFormatter;
import com.castruche.laboratory_api.myworld_api.formatter.PostFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    private PostService service;
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostFormatter postFormatter;

    @Mock
    private FileService fileService;

    @Mock
    private PictureService pictureService;


    @BeforeEach
    public void setUp() {
        service = new PostService(postRepository,postFormatter, pictureService, fileService);
    }

    @Test
    void createWithFiles_ShouldCreatePostAndAddPictures_WhenFilesAreNotNull() {
        //TODO FINIR TEST
    }

    @Test
    void updateWithFiles_ShouldUpdatePostAndAddPictures_WhenFilesAreNotNull() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostAndFiles_ShouldDeletePostAndFiles_WhenPostIsNotNull() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostPicture_ShouldDeletePostPicture_WhenPostIsNotNull() {
        //TODO FINIR TEST
    }

}
