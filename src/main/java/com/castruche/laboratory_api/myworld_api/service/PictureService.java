package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.main_api.service.GenericService;
import com.castruche.laboratory_api.myworld_api.dao.PictureRepository;
import com.castruche.laboratory_api.myworld_api.dto.picture.PictureDto;
import com.castruche.laboratory_api.myworld_api.dto.picture.PictureResizedDto;
import com.castruche.laboratory_api.myworld_api.entity.Picture;
import com.castruche.laboratory_api.myworld_api.entity.Post;
import com.castruche.laboratory_api.myworld_api.formatter.PictureFormatter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;

@Service
public class PictureService extends GenericService<Picture, PictureDto, PictureResizedDto> {

    private static final Logger logger = LogManager.getLogger(PictureService.class);

    private final FileService fileService;
    private final PictureFormatter pictureFormatter;
    private final PictureRepository repository;

    public PictureService(PictureRepository pictureRepository, PictureFormatter pictureFormatter, FileService fileService) {
        super(pictureRepository, pictureFormatter);
        this.pictureFormatter = pictureFormatter;
        this.repository = pictureRepository;
        this.fileService = fileService;
    }


    public ResponseEntity<Resource> downloadResizedPicture(Long pictureId) {
        // TODO: check if the file is accessible by the user   ET INCLURE DANS TEST
        if (null == pictureId) return ResponseEntity.notFound().build();
        Picture picture = repository.findById(pictureId).orElse(null);
        if (null == picture || null == picture.getResizedFileFilepath()) {
            return ResponseEntity.notFound().build();
        }
        return fileService.downloadPictureFromPath(picture.getResizedFileFilepath());
    }

    public ResponseEntity<Resource> downloadOriginalPicture(Long pictureId) {
        // TODO: check if the file is accessible by the user   ET INCLURE DANS TEST
        if (null == pictureId) return ResponseEntity.notFound().build();
        Picture picture = repository.findById(pictureId).orElse(null);
        if (null == picture || null == picture.getOriginalFilepath()) {
            return ResponseEntity.notFound().build();
        }
        return fileService.downloadPictureFromPath(picture.getOriginalFilepath());
    }

    public ResponseEntity<Resource> downloadMiniaturePicture(Long pictureId) {
        // TODO: check if the file is accessible by the user  ET INCLURE DANS TEST
        if (null == pictureId) return ResponseEntity.notFound().build();
        Picture picture = repository.findById(pictureId).orElse(null);
        if (null == picture || null == picture.getMiniatureFileFilepath()) {
            return ResponseEntity.notFound().build();
        }
        return fileService.downloadPictureFromPath(picture.getMiniatureFileFilepath());
    }

    public List<Picture> convertFilesToPictures(List<MultipartFile> files, Post post, String suffix) {
        List<Picture> results = pictureFormatter.filesToEntities(files, suffix);
        for (Picture picture : results) {
            picture.setPost(post);
            picture.setOriginalFilepath(fileService.generateOriginalPostPictureFilepath(picture.getFilename(), post.getCreationBy().getId(), post.getId()));
            picture.setResizedFileFilepath(fileService.generateResizedPostPictureFilepath(picture.getFilename(), post.getCreationBy().getId(), post.getId()));
            picture.setMiniatureFileFilepath(fileService.generateMiniaturePostPictureFilepath(picture.getFilename(), post.getCreationBy().getId(), post.getId()));
        }
        return results;
    }

    public ResponseEntity<Boolean> checkPictureStatus() {
        //TODO VERIF UNIQUEMENT POUR ADMIN ET INCLURE DANS TEST!!!
        List<Picture> allPictures = repository.findAll();
        //On va chercher chaque photo et vérifier si le fichier original existe
        for (Picture picture : allPictures) {
            if (Strings.isEmpty(picture.getOriginalFilepath()) || Strings.isEmpty(picture.getFilename())
                    || null == picture.getPost()) {
                //Si il manque une information capitale on supprime la photo et ses fichiers
                repository.delete(picture);
                logger.info("Picture deleted : " + picture.getId());
                fileService.deleteAllFormatsFromPostPicture(picture);
                continue;
            }
            try {
                File originalFile = null;
                if (Strings.isNotEmpty(picture.getOriginalFilepath())) {
                    //Si le fichier original n'existe plus on supprime la photo et ses fichiers
                    if (!Files.exists(Paths.get(picture.getOriginalFilepath()))){
                        repository.delete(picture);
                        logger.info("Picture deleted : " + picture.getId());
                        fileService.deleteAllFormatsFromPostPicture(picture);
                    }
                    else{
                       originalFile= new File(picture.getOriginalFilepath());
                    }
                }

                if (null != originalFile) {
                    if (Strings.isEmpty(picture.getResizedFileFilepath())) {
                        picture.setResizedFileFilepath(fileService.generateResizedPostPictureFilepath(picture.getFilename(), picture.getPost().getCreationBy().getId(), picture.getPost().getId()));
                        repository.save(picture);
                    }
                    if(!Files.exists(Paths.get(picture.getResizedFileFilepath()))){
                        fileService.savePostPictureResizedFile(originalFile, picture.getFilename(), picture.getPost().getCreationBy().getId(), picture.getPost().getId());
                    }

                    if(Strings.isEmpty(picture.getMiniatureFileFilepath())){
                        picture.setMiniatureFileFilepath(fileService.generateMiniaturePostPictureFilepath(picture.getFilename(), picture.getPost().getCreationBy().getId(), picture.getPost().getId()));
                        repository.save(picture);
                    }
                    if(!Files.exists(Paths.get(picture.getMiniatureFileFilepath()))){
                        fileService.savePostPictureMiniatureFile(originalFile, picture.getFilename(), picture.getPost().getCreationBy().getId(), picture.getPost().getId());
                    }
                }

            } catch (Exception e) {
                logger.error("Erreur lors de la vérification de la picture : " + picture.getId(), e);
            }

        }
        return ResponseEntity.ok(true);
    }


}
