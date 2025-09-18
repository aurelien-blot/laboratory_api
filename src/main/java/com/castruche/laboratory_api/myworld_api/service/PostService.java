package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.main_api.service.GenericService;
import com.castruche.laboratory_api.myworld_api.dao.PostRepository;
import com.castruche.laboratory_api.myworld_api.dto.post.PostDto;
import com.castruche.laboratory_api.myworld_api.entity.Picture;
import com.castruche.laboratory_api.myworld_api.entity.Post;
import com.castruche.laboratory_api.myworld_api.formatter.PostFormatter;
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
    public PostDto createWithFiles(PostDto dto, List<MultipartFile> files) {
        PostDto postDtoCreated = create(dto);
        return addPostPictureFiles(postDtoCreated.getId(), postDtoCreated, files);
    }

    @Transactional
    public PostDto updateWithFiles(Long id, PostDto dto, List<MultipartFile> files) {
        PostDto updatedDto = update(dto);
        return addPostPictureFiles(id, updatedDto, files);
    }

    private PostDto addPostPictureFiles(Long id, PostDto dto, List<MultipartFile> files){
        if(files!=null && !files.isEmpty()){
            Post entity = selectById(id);
            String filenameSuffix = "_"+(new Date().getTime());
            List<Picture> pictures = pictureService.convertFilesToPictures(files, entity, filenameSuffix);
            pictureService.createAll(pictures);
            entity.setModificationTime(LocalDateTime.now());
            for(MultipartFile file : files){
                String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                String filenameWithoutExtension = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
                String newFileName = filenameWithoutExtension+filenameSuffix+extension;
                File originalFile = fileService.savePostPictureOriginalFile(file, newFileName, entity.getCreationBy().getId(), entity.getId());
                fileService.savePostPictureMiniatureFile(originalFile,newFileName,  entity.getCreationBy().getId(), entity.getId());
                fileService.savePostPictureResizedFile(originalFile,newFileName,  entity.getCreationBy().getId(), entity.getId());
            }

            dto = selectDtoById(entity.getId());
        }
        return dto;
    }

    @Transactional
    public void deletePostAndFiles(Long id) {
        Post entity = selectById(id);
        if(null!=entity.getPictureList()){
            for(Picture picture : entity.getPictureList()){
                fileService.deletePostPictureOriginalFolder(entity.getCreationBy().getId(), picture.getPost().getId());
                fileService.deletePostPictureResizedFolder(entity.getCreationBy().getId(), picture.getPost().getId());
            }
        }
        delete(entity.getId());
    }

    @Transactional
    public void deletePostPicture(Long postId, Long pictureId) {
        Picture picture = pictureService.selectById(pictureId);
        //On check si le picture appartient au post au cas où
        if(null!=picture.getPost() && picture.getPost().getId().equals(postId)){
            fileService.deletePostPictureOriginalFile(picture);
            fileService.deletePostPictureResizedFile(picture);
            pictureService.delete(pictureId);
        }
        else{
            throw new RuntimeException("Le picture "+pictureId+" n'appartient pas au post "+postId);
        }
    }


}
