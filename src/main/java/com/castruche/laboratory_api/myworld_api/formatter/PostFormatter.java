package com.castruche.laboratory_api.myworld_api.formatter;

import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.myworld_api.dto.post.PostDto;
import com.castruche.laboratory_api.myworld_api.entity.Picture;
import com.castruche.laboratory_api.myworld_api.entity.Post;
import com.castruche.laboratory_api.myworld_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostFormatter implements IFormatter<Post, PostDto, PostDto> {

    private UserFormatter userFormatter;
    private UserService userService;
    private PictureFormatter pictureFormatter;

    public PostFormatter(UserFormatter userFormatter, UserService userService, PictureFormatter pictureFormatter) {
        this.userFormatter = userFormatter;
        this.userService = userService;
        this.pictureFormatter = pictureFormatter;
    }

    @Override
    public PostDto entityToDto(Post entity) {
        if(entity == null){
            return null;
        }
        PostDto dto = new PostDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreationTime(entity.getCreationTime());
        dto.setModificationTime(entity.getModificationTime());
        if(entity.getCreationBy()!=null && entity.getCreationBy().getId() != null){
            dto.setCreationBy(userFormatter.entityToLightDto(entity.getCreationBy()));
        }
        if(entity.getPictureList() != null){
            dto.setPictureList(pictureFormatter.entityToLightDto(entity.getPictureList()));
        }
        return dto;
    }

    @Override
    public PostDto entityToLightDto(Post post) {
        return null;
    }

    @Override
    public Post dtoToEntity(PostDto dto) {
        Post entity = new Post();
        entity.setId(dto.getId());
        entity.setCreationTime(dto.getCreationTime());
        entity.setModificationTime(dto.getModificationTime());
        if(dto.getCreationBy() != null && dto.getCreationBy().getId() != null){
            entity.setCreationBy(userService.selectById(dto.getCreationBy().getId()));
        }

        return updateEntityFromDto(entity, dto);
    }

    @Override
    public List<PostDto> entityToLightDto(List<Post> list) {
        return IFormatter.super.entityToLightDto(list);
    }

    @Override
    public List<PostDto> entityToDto(List<Post> list) {
        return IFormatter.super.entityToDto(list);
    }

    @Override
    public Post updateEntityFromDto(Post entity, PostDto dto) {
        entity.setContent(dto.getContent());
        return entity;
    }
}
