package com.castruche.laboratory_api.myworld_api.dto.post;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;
import com.castruche.laboratory_api.myworld_api.dto.picture.PictureDto;
import com.castruche.laboratory_api.myworld_api.dto.picture.PictureResizedDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;

import java.util.List;

public class PostDto extends AbstractDto {

    private String content;

    private UserLightDto creationBy;


    private List<PictureResizedDto> pictureList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserLightDto getCreationBy() {
        return creationBy;
    }

    public void setCreationBy(UserLightDto creationBy) {
        this.creationBy = creationBy;
    }

    public List<PictureResizedDto> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<PictureResizedDto> pictureList) {
        this.pictureList = pictureList;
    }
}
