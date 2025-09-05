package com.castruche.laboratory_api.myworld_api.dto.post;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;

public class PostDto extends AbstractDto {

    private String content;

    private UserLightDto creationBy;

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
}
