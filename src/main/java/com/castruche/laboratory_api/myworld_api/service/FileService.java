package com.castruche.laboratory_api.myworld_api.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class FileService {

    private static final Logger logger = LogManager.getLogger(FileService.class);

    private static final String ORIGINAL_FOLDER = "original";
    private static final String RESIZED_FOLDER = "resized";

    @Value("${myworld.upload.dir}")
    private String uploadDir;

    @Value("${myworld.upload.max-width}")
    private int resizedMaxWidth;

    @Value("${myworld.upload.max-height}")
    private int resizedMaxHeight;

    public String generateResizedPostPictureFilepath(String filename, Long userId, Long postId) {
        return uploadDir + "/" + RESIZED_FOLDER + generateFilePathEnd(filename, userId, postId);
    }

    public String generateOriginalPostPictureFilepath(String filename, Long userId, Long postId) {
        return uploadDir + "/" + ORIGINAL_FOLDER + generateFilePathEnd(filename, userId, postId);
    }

    private String generateFilePathEnd(String filename, Long userId, Long postId) {
        return "/" + userId + "/posts/" + postId + "/" + filename;
    }

    public File savePostPictureOriginalFile(MultipartFile file, Long userId, Long postId) {
        if (null == file) {
            return null;
        }

        String filename = file.getOriginalFilename();
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

    public void savePostPictureResizedFile(File file, Long userId, Long postId) {
        if (null == file) {
            return;
        }
        String filename = file.getName();
        String resizedFilepath = generateResizedPostPictureFilepath(filename, userId, postId);
        try {
            File resizedFile = new File(resizedFilepath);
            resizedFile.getParentFile().mkdirs();
            Thumbnails.of(file)
                    .size(resizedMaxWidth, resizedMaxHeight)
                    .outputFormat("jpg")
                    .outputQuality(0.85)
                    .toFile(resizedFile);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du fichier redimensionné : " + resizedFilepath, e);
        }

    }
}
