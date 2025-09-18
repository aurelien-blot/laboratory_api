package com.castruche.laboratory_api.myworld_api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
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
public class FileServiceTest {

    @Value("${myworld.upload.dir}")
    private String uploadDir;
    FileService service;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setup() {
        FileService real = new FileService();
        service = spy(real);
    }

    static void setField(Object target, String name, Object value) {
        try {
            var f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private File createTempImage(Path path, int w, int h, String format) throws Exception {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        File f = path.toFile();
        f.getParentFile().mkdirs();
        ImageIO.write(img, format, f);
        return f;
    }

    @Test
    void generateResizedPostPictureFilepath_shouldReturnCorrectPath() {
        String fileName = "test.png";
        String path =service.generateResizedPostPictureFilepath(fileName, 2L, 3L);
        assertThat(path).isEqualTo(uploadDir + "/resized/2/posts/3/"+fileName);
    }

    @Test
    void generateResizedPostPictureFilepath_shouldReturnIncorrectPath() {
        String fileName = "test.png";
        String path =service.generateResizedPostPictureFilepath("test.png", 2L, 3L);
        assertThat(path).isNotEqualTo(uploadDir + "X/resized/2/posts/3/"+fileName);
        assertThat(path).isNotEqualTo(uploadDir + "/resizeXd/2/posts/3/"+fileName);
        assertThat(path).isNotEqualTo(uploadDir + "/resized/21/posts/3/"+fileName);
        assertThat(path).isNotEqualTo(uploadDir + "/resized/2/pXosts/3/"+fileName);
        assertThat(path).isNotEqualTo(uploadDir + "/resized/2/posts/32/"+fileName);
    }

    @Test
    void generateOriginalPostPictureFilepath_shouldReturnCorrectPath() {
        String fileName = "test.png";
        String path =service.generateOriginalPostPictureFilepath(fileName, 2L, 3L);
        assertThat(path).isEqualTo(uploadDir + "/original/2/posts/3/"+fileName);
    }

    @Test
    void generateOriginalPostPictureFilepath_shouldReturnIncorrectPath() {
        String fileName = "test.png";
        String path =service.generateOriginalPostPictureFilepath(fileName, 2L, 3L);
        assertThat(path).isNotEqualTo(uploadDir + "X/original/2/posts/3/"+fileName);
        assertThat(path).isNotEqualTo(uploadDir + "/originalX/2/posts/3/"+fileName);
        assertThat(path).isNotEqualTo(uploadDir + "/original/21/posts/3/"+fileName);
        assertThat(path).isNotEqualTo(uploadDir + "/original/2/pXosts/3/"+fileName);
        assertThat(path).isNotEqualTo(uploadDir + "/original/2/posts/32/"+fileName);
    }

    @Test
    void savePostPictureOriginalFile_returnsNull_whenFileIsNull() {
        File f = service.savePostPictureOriginalFile(null, "a.jpg", 1L, 2L);
        assertThat(f).isNull();
    }

    @Test
    void savePostPictureOriginalFile_createsParents_writesFile_andReturnsIt_onSuccess() throws Exception {
        byte[] bytes = "hello".getBytes();
        MockMultipartFile mf = new MockMultipartFile("file", "a.jpg", "image/jpeg", bytes);

        Path target = tempDir.resolve("users/1/posts/2/original/a.jpg");
        doReturn(target.toString())
                .when(service).generateOriginalPostPictureFilepath("a.jpg", 1L, 2L);

        File out = service.savePostPictureOriginalFile(mf, "a.jpg", 1L, 2L);

        assertThat(out).isNotNull();
        Assertions.assertThat(out.toPath()).exists().isRegularFile();

        assertThat(out.getParentFile()).exists();
        assertThat(Files.readAllBytes(out.toPath())).isEqualTo(bytes);
        verify(service).generateOriginalPostPictureFilepath("a.jpg", 1L, 2L);
    }

    @Test
    void savePostPictureOriginalFile_returnsNull_onIOException() throws Exception {
        MultipartFile mf = mock(MultipartFile.class);
        Path target = tempDir.resolve("x/y/z.jpg");
        doReturn(target.toString())
                .when(service).generateOriginalPostPictureFilepath("z.jpg", 9L, 9L);

        doThrow(new IOException("ERROR")).when(mf).transferTo(any(File.class));

        File out = service.savePostPictureOriginalFile(mf, "z.jpg", 9L, 9L);

        assertThat(out).isNull();
        verify(mf).transferTo(any(File.class));
    }

    @Test
    void savePostPictureResizedFile_shouldDoNothing_whenFileIsNull() {
        service.savePostPictureResizedFile(null, "x.jpg", 1L, 2L);
        verify(service, never()).generateResizedPostPictureFilepath(any(), anyLong(), anyLong());
    }

    @Test
    void savePostPictureResizedFile_shouldCreateResizedImage_withinMaxBounds() throws Exception {
        setField(service, "resizedMaxWidth", 200);
        setField(service, "resizedMaxHeight", 120);

        Path src = tempDir.resolve("src/big.png");
        File input = createTempImage(src, 800, 600, "png");

        Path target = tempDir.resolve("users/1/posts/2/resized/out.jpg");
        doReturn(target.toString())
                .when(service).generateResizedPostPictureFilepath("out.jpg", 1L, 2L);

        service.savePostPictureResizedFile(input, "out.jpg", 1L, 2L);

        File out = target.toFile();
        assertThat(out).exists().isFile();

        BufferedImage resized = ImageIO.read(out);
        assertThat(resized.getWidth()).isLessThanOrEqualTo(200);
        assertThat(resized.getHeight()).isLessThanOrEqualTo(120);

        verify(service).generateResizedPostPictureFilepath("out.jpg", 1L, 2L);
    }

    @Test
    void savePostPictureResizedFile_shouldCatchException_andNotThrow() throws Exception {
        setField(service, "resizedMaxWidth", 100);
        setField(service, "resizedMaxHeight", 100);

        File input = createTempImage(tempDir.resolve("src/in.png"), 50, 50, "png");
        Path tricky = tempDir.resolve("not_a_dir");
        Files.writeString(tricky, "file");
        Path impossible = tricky.resolve("out.jpg");

        doReturn(impossible.toString())
                .when(service).generateResizedPostPictureFilepath("x.jpg", 1L, 2L);

        service.savePostPictureResizedFile(input, "x.jpg", 1L, 2L);
    }

    //TODO FINIR SERVICE TEST SUR LES DELETES

    @Test
    void deletePostPictureOriginalFolder_shouldDeleteFolder_whenFolderExists() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostPictureOriginalFolder_shouldThrowException_whenFolderDoesNotExist() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostPictureResizedFolder_shouldDeleteFolder_whenFolderExists() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostPictureResizedFolder_shouldThrowException_whenFolderDoesNotExist() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostPictureOriginalFile_shouldDeleteFile_whenFileExists() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostPictureOriginalFile_shouldThrowException_whenFileDoesNotExist() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostPictureResizedFile_shouldDeleteFile_whenFileExists() {
        //TODO FINIR TEST
    }

    @Test
    void deletePostPictureResizedFile_shouldThrowException_whenFileDoesNotExist() {
        //TODO FINIR TEST
    }

    @Test
    void downloadPictureFromPath_shouldReturnResponseEntity_whenFileExists() throws Exception{
        String filePath = "test/test.jpg";
        File input = createTempImage(tempDir.resolve(filePath), 50, 50, "png");
        ResponseEntity<Resource> response = service.downloadPictureFromPath(input.toPath().toString());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void downloadPictureFromPath_shouldReturnNotFound_whenFileDoesNotExist() {
        String filePath = "test/test.jpg";
        ResponseEntity<Resource> response = service.downloadPictureFromPath(filePath);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
