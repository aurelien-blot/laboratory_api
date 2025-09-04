package com.castruche.laboratory_api.myworld_api.dto.post;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class PostDto extends AbstractDto {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
