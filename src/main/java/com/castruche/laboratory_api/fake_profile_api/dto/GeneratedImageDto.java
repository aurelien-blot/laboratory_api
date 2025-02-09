package com.castruche.laboratory_api.fake_profile_api.dto;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class GeneratedImageDto extends AbstractDto {

    private String templateTitle;
    private Long seedUsed;
    private Long subseedUsed;

    private String filePath;

    private String prompt;
    private String negativePrompt;

    private int cfgScale;
    private int width;
    private int height;

    private Double denoisingStrength;

    private String refinerCheckpoint;
    private int refinerSwitchAt;

    private boolean sendImages;

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public Long getSeedUsed() {
        return seedUsed;
    }

    public void setSeedUsed(Long seedUsed) {
        this.seedUsed = seedUsed;
    }

    public Long getSubseedUsed() {
        return subseedUsed;
    }

    public void setSubseedUsed(Long subseedUsed) {
        this.subseedUsed = subseedUsed;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        if(filePath == null){
            return null;
        }
        return filePath.substring(filePath.lastIndexOf('\\') + 1);
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getNegativePrompt() {
        return negativePrompt;
    }

    public void setNegativePrompt(String negativePrompt) {
        this.negativePrompt = negativePrompt;
    }

    public int getCfgScale() {
        return cfgScale;
    }

    public void setCfgScale(int cfgScale) {
        this.cfgScale = cfgScale;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Double getDenoisingStrength() {
        return denoisingStrength;
    }

    public void setDenoisingStrength(Double denoisingStrength) {
        this.denoisingStrength = denoisingStrength;
    }

    public String getRefinerCheckpoint() {
        return refinerCheckpoint;
    }

    public void setRefinerCheckpoint(String refinerCheckpoint) {
        this.refinerCheckpoint = refinerCheckpoint;
    }

    public int getRefinerSwitchAt() {
        return refinerSwitchAt;
    }

    public void setRefinerSwitchAt(int refinerSwitchAt) {
        this.refinerSwitchAt = refinerSwitchAt;
    }

    public boolean isSendImages() {
        return sendImages;
    }

    public void setSendImages(boolean sendImages) {
        this.sendImages = sendImages;
    }
}
