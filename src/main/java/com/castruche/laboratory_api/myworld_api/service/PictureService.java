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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Transactional
    public List<Picture> createAll(List<Picture> entities){
        return repository.saveAll(entities);
    }

    public List<Picture> convertFilesToPictures(List<MultipartFile> files, Post post){
        List<Picture> results = pictureFormatter.filesToEntities(files);
        for(Picture picture : results){
            picture.setPost(post);
            picture.setOriginalFilepath(fileService.generateOriginalPostPictureFilepath(picture.getFilename(), post.getCreationBy().getId(), post.getId()));
            picture.setResizedFileFilepath(fileService.generateResizedPostPictureFilepath(picture.getFilename(), post.getCreationBy().getId(), post.getId()));
        }
        return results;
    }






}
