package com.castruche.laboratory_api.myworld_api.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
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
        return generateFilePath(RESIZED_FOLDER, filename, userId, postId);
    }

    public String generateOriginalPostPictureFilepath(String filename, Long userId, Long postId) {
        return generateFilePath(ORIGINAL_FOLDER, filename, userId, postId);
    }

    private String generateFilePath(String type, String filename, Long userId, Long postId) {
        return generateFolderPath(type, userId, postId)+ "/" + filename;
    }

    private String generateFolderPath(String type, Long userId, Long postId) {
        return uploadDir + "/" + type + "/" + userId + "/posts/" + postId ;
    }

    public File savePostPictureOriginalFile(MultipartFile file,String filename, Long userId, Long postId) {
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

    public void savePostPictureResizedFile(File file,String filename, Long userId, Long postId) {
        if (null == file) {
            return;
        }
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

    public void deletePostPictureOriginalFolder(Long userId, Long postId){
        deletePostPictureFolder(ORIGINAL_FOLDER, userId, postId);
    }

    public void deletePostPictureResizedFolder(Long userId, Long postId){
        deletePostPictureFolder(RESIZED_FOLDER, userId, postId);
    }

    private void deletePostPictureFolder(String type, Long userId, Long postId){
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
        }
        catch (Exception e) {
            logger.error("Erreur lors de la suppression du dossier : " + folderPath, e);
        }
    }
}
