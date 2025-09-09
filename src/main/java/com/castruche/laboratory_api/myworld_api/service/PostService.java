package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.main_api.service.GenericService;
import com.castruche.laboratory_api.myworld_api.dao.PostRepository;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.dto.post.PostDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;
import com.castruche.laboratory_api.myworld_api.entity.Picture;
import com.castruche.laboratory_api.myworld_api.entity.Post;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.formatter.PostFormatter;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PostService extends GenericService<Post, PostDto, PostDto> {

    private static final Logger logger = LogManager.getLogger(PostService.class);
    private final PostFormatter postFormatter;
    private final PostRepository postRepository;
    private final PictureService pictureService;
    private final FileService fileService;

    public PostService(PostRepository postRepository, PostFormatter postFormatter, PictureService pictureService, FileService fileService) {
        super(postRepository, postFormatter);
        this.postFormatter = postFormatter;
        this.pictureService = pictureService;
        this.postRepository = postRepository;
        this.fileService = fileService;
    }

    @Transactional
    public PostDto createWithFiles(PostDto postDto, List<MultipartFile> files) {
        PostDto postDtoCreated = create(postDto);

        if(files!=null && !files.isEmpty()){
            Post entity = selectById(postDtoCreated.getId());
            List<Picture> pictures = pictureService.convertFilesToPictures(files, entity);
            pictureService.createAll(pictures);
            entity.setModificationTime(LocalDateTime.now());
            for(MultipartFile file : files){
                File originalFile = fileService.savePostPictureOriginalFile(file, entity.getCreationBy().getId(), entity.getId());
                fileService.savePostPictureResizedFile(originalFile, entity.getCreationBy().getId(), entity.getId());
            }

            postDtoCreated = selectDtoById(entity.getId());
        }

        return postDtoCreated;
    }


}
