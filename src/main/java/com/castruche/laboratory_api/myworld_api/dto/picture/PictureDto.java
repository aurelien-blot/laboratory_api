package com.castruche.laboratory_api.myworld_api.dto.picture;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;

public class PictureDto extends AbstractDto {

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
