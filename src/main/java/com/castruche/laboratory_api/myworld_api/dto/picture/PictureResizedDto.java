package com.castruche.laboratory_api.myworld_api.dto.picture;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class PictureResizedDto extends AbstractDto {

    private String filename;

    private String filePath;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
