package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.myworld_api.entity.Picture;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileService {

    private static final Logger logger = LogManager.getLogger(FileService.class);

    private static final String ORIGINAL_FOLDER = "original";
    private static final String RESIZED_FOLDER = "resized";
    private static final String MINIATURE_FOLDER = "miniature";

    @Value("${myworld.upload.dir}")
    private String uploadDir;

    @Value("${myworld.upload.resized-max-width}")
    private int resizedMaxWidth;

    @Value("${myworld.upload.resized-max-height}")
    private int resizedMaxHeight;

    @Value("${myworld.upload.miniature-max-width}")
    private int miniatureMaxWidth;

    @Value("${myworld.upload.miniature-max-height}")
    private int miniatureMaxHeight;

    public String generateMiniaturePostPictureFilepath(String filename, Long userId, Long postId) {
        return generateFilePath(MINIATURE_FOLDER, filename, userId, postId);
    }

    public String generateResizedPostPictureFilepath(String filename, Long userId, Long postId) {
        return generateFilePath(RESIZED_FOLDER, filename, userId, postId);
    }

    public String generateOriginalPostPictureFilepath(String filename, Long userId, Long postId) {
        return generateFilePath(ORIGINAL_FOLDER, filename, userId, postId);
    }

    private String generateFilePath(String type, String filename, Long userId, Long postId) {
        return generateFolderPath(type, userId, postId) + "/" + filename;
    }

    private String generateFolderPath(String type, Long userId, Long postId) {
        return uploadDir + "/" + type + "/" + userId + "/posts/" + postId;
    }

    public File savePostPictureOriginalFile(MultipartFile file, String filename, Long userId, Long postId) {
        if (null == file) {
            return null;
        }
        String originalFilepath = generateOriginalPostPictureFilepath(filename, userId, postId);
        try {
            File originalFile = new File(originalFilepath);
            originalFile.getParentFile().mkdirs();
            file.transferTo(originalFile);
            return originalFile;
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du fichier original : " + originalFilepath, e);
        }
        return null;
    }

    public void savePostPictureMiniatureFile(File file, String filename, Long userId, Long postId) {
        if (null == file) {
            return;
        }
        String miniatureFilepath = generateMiniaturePostPictureFilepath(filename, userId, postId);
        try {
            File miniatureFile = new File(miniatureFilepath);
            miniatureFile.getParentFile().mkdirs();
            Thumbnails.of(file)
                    .size(miniatureMaxWidth, miniatureMaxHeight)
                    //.outputFormat("jpg")
                    .outputQuality(0.85)
                    .toFile(miniatureFile);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du fichier redimensionné : " + miniatureFilepath, e);
        }
    }

    public void savePostPictureResizedFile(File file, String filename, Long userId, Long postId) {
        if (null == file) {
            return;
        }
        String resizedFilepath = generateResizedPostPictureFilepath(filename, userId, postId);
        try {
            File resizedFile = new File(resizedFilepath);
            resizedFile.getParentFile().mkdirs();
            Thumbnails.of(file)
                    .size(resizedMaxWidth, resizedMaxHeight)
                    //.outputFormat("jpg")
                    .outputQuality(0.85)
                    .toFile(resizedFile);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du fichier redimensionné : " + resizedFilepath, e);
        }
    }

    public void deletePostPictureOriginalFolder(Long userId, Long postId) {
        deletePostPictureFolder(ORIGINAL_FOLDER, userId, postId);
    }

    public void deletePostPictureResizedFolder(Long userId, Long postId) {
        deletePostPictureFolder(RESIZED_FOLDER, userId, postId);
    }

    public void deletePostPictureOriginalFile(Picture picture) {
        String originalFilepath = generateOriginalPostPictureFilepath(picture.getFilename(), picture.getPost().getCreationBy().getId(), picture.getPost().getId());
        try {
            File originalFile = new File(originalFilepath);
            originalFile.delete();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du fichier original : " + originalFilepath, e);
        }

    }

    public void deletePostPictureResizedFile(Picture picture) {
        String resizedFilepath = generateResizedPostPictureFilepath(picture.getFilename(), picture.getPost().getCreationBy().getId(), picture.getPost().getId());
        try {
            File resizedFile = new File(resizedFilepath);
            resizedFile.delete();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du fichier original : " + resizedFilepath, e);
        }

    }

    public void deletePostPictureMiniatureFile(Picture picture) {
        String miniatureFilepath = generateMiniaturePostPictureFilepath(picture.getFilename(), picture.getPost().getCreationBy().getId(), picture.getPost().getId());
        try {
            File miniatureFile = new File(miniatureFilepath);
            miniatureFile.delete();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du fichier original : " + miniatureFilepath, e);
        }
    }

    private void deletePostPictureFolder(String type, Long userId, Long postId) {
        String folderPath = generateFolderPath(type, userId, postId);
        try {
            File folder = new File(folderPath);
            if (folder.exists()) {
                File[] files = folder.listFiles();
                for (File file : files) {
                    file.delete();
                }
                folder.delete();
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du dossier : " + folderPath, e);
        }
    }

    public ResponseEntity<Resource> downloadPictureFromPath(String filePath) {
        Path p = Path.of(filePath);
        if (!Files.exists(p)) return ResponseEntity.notFound().build();

        try {
            Resource res = new UrlResource(p.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .cacheControl(CacheControl.noCache()) // ou max-age court si tu veux
                    .body(res);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteAllFormatsFromPostPicture(Picture picture) {
        deletePostPictureOriginalFile(picture);
        deletePostPictureResizedFile(picture);
        deletePostPictureMiniatureFile(picture);
    }

}
