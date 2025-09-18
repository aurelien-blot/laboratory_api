package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.myworld_api.dao.PictureRepository;
import com.castruche.laboratory_api.myworld_api.dao.PostRepository;
import com.castruche.laboratory_api.myworld_api.entity.Picture;
import com.castruche.laboratory_api.myworld_api.entity.Post;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.formatter.PictureFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PictureServiceTest {

    @TempDir
    Path tempDir;

    PictureService service;

    @Mock
    private PictureRepository repository;

    @Mock
    private PictureFormatter formatter;

    @Mock
    private FileService fileService;

    @BeforeEach
    public void setUp() {
        service = new PictureService(repository, formatter, fileService);
    }

    @Test
    void convertFilesToPictures_shouldConvertFilesToPictures() {
        Post post = new Post();
        post.setId(1L);
        User creationBy = new User();
        creationBy.setId(1L);
        post.setCreationBy(creationBy);
        List<Picture> expected = new ArrayList<>();
        Picture picture = new Picture();
        picture.setPost(post);
        picture.setFilename("test.txt");
        picture.setResizedFileFilepath("test");
        picture.setOriginalFilepath("test");
        expected.add(picture);
        List<MultipartFile> files = List.of(new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()));

        when(formatter.filesToEntities(files, "test")).thenReturn(expected);
        when(fileService.generateOriginalPostPictureFilepath("test.txt", post.getCreationBy().getId(), post.getId())).thenReturn("test");
        when(fileService.generateResizedPostPictureFilepath("test.txt", post.getCreationBy().getId(), post.getId())).thenReturn("test");

        List<Picture> result = service.convertFilesToPictures(files, post, "test");

        assertEquals(expected, result);
        verify(formatter).filesToEntities(files, "test");
        verify(fileService).generateOriginalPostPictureFilepath("test.txt", post.getCreationBy().getId(), post.getId());
        verify(fileService).generateResizedPostPictureFilepath("test.txt", post.getCreationBy().getId(), post.getId());
    }

    @Test
    void checkPictureStatus_ShouldDeleteFilesAndPictures_WhenOriginalPathIsMissing() {
        Post post = new Post();
        post.setId(1L);

        Picture picture1 = new Picture();
        picture1.setId(1L);
        picture1.setPost(post);
        picture1.setFilename("test.jpg");
        picture1.setOriginalFilepath(null);
        when(repository.findAll()).thenReturn(List.of(picture1));

        ResponseEntity<Boolean> result = service.checkPictureStatus();

        assertEquals(Boolean.TRUE, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        InOrder inOrder = inOrder(repository, fileService);
        inOrder.verify(repository).findAll();
        inOrder.verify(repository).delete(picture1);
        inOrder.verify(fileService).deleteAllFormatsFromPostPicture(picture1);
        verifyNoMoreInteractions(fileService);
    }

    @Test
    void checkPictureStatus_ShouldDelete_whenOriginalFileDoesNotExist() {
        Picture p = new Picture();
        p.setId(1L);
        Post post = new Post();
        post.setId(1L);
        p.setPost(post);
        p.setFilename("test.jpg");
        //Chemin inexistant
        p.setOriginalFilepath(tempDir.resolve("missing/original.jpg").toString());
        when(repository.findAll()).thenReturn(List.of(p));

        ResponseEntity<Boolean> result = service.checkPictureStatus();

        assertEquals(Boolean.TRUE, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

        verify(repository).delete(p);
        verify(fileService).deleteAllFormatsFromPostPicture(p);
    }

    @Test
    void checkPictureStatus_ShouldGenerateResizedAndMini_whenOriginalExistsAndTargetsMissing() throws Exception {
        String filename = "out.jpg";

        Picture p = new Picture();
        Post post = new Post();
        post.setId(2L);
        User user = new User();
        user.setId(1L);
        post.setCreationBy(user);
        p.setPost(post);
        p.setFilename(filename);

        Path original = tempDir.resolve("users/1/posts/2/original/out.jpg");
        Files.createDirectories(original.getParent());
        Files.writeString(original, "data");
        p.setOriginalFilepath(original.toString());
        p.setResizedFileFilepath(null);
        p.setMiniatureFileFilepath(null);

        when(repository.findAll()).thenReturn(List.of(p));

        String resizedPath = tempDir.resolve("users/1/posts/2/resized/out.jpg").toString();
        String miniPath    = tempDir.resolve("users/1/posts/2/miniature/out.jpg").toString();

        when(fileService.generateResizedPostPictureFilepath(filename, 1L, 2L))
                .thenReturn(resizedPath);
        when(fileService.generateMiniaturePostPictureFilepath(filename, 1L, 2L))
                .thenReturn(miniPath);

        ResponseEntity<Boolean> result = service.checkPictureStatus();

        assertEquals(Boolean.TRUE, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

        verify(repository, times(2)).save(p);

        // Vérifie les appels aux générateurs de chemins
        InOrder order = inOrder(fileService);
        order.verify(fileService).generateResizedPostPictureFilepath(filename, 1L, 2L);
        order.verify(fileService).savePostPictureResizedFile(
                eq(new File(original.toString())), eq(filename), eq(1L), eq(2L));
        order.verify(fileService).generateMiniaturePostPictureFilepath(filename, 1L, 2L);
        order.verify(fileService).savePostPictureMiniatureFile(
                eq(new File(original.toString())), eq(filename), eq(1L), eq(2L));

        order.verifyNoMoreInteractions();
    }




}
