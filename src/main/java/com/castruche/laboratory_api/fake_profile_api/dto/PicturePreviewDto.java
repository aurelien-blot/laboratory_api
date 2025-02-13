package com.castruche.laboratory_api.fake_profile_api.dto;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class PicturePreviewDto  {

    private String base64Image;

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
