package com.castruche.laboratory_api.fake_profile_api.dto;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class ImageTypeDto extends AbstractDto {

    private String name;
    private String description;
    private Long seed;
    private Long subseed;
    private Integer width;
    private Integer height;
    private String samplerName;
    private Integer cfgScale;
    private Integer steps;
    private Boolean restoreFaces;
    private String faceRestorationModel;
    private String sdModelCheckpoint;
    private Double denoisingStrength;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public Long getSubseed() {
        return subseed;
    }

    public void setSubseed(Long subseed) {
        this.subseed = subseed;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSamplerName() {
        return samplerName;
    }

    public void setSamplerName(String samplerName) {
        this.samplerName = samplerName;
    }

    public Integer getCfgScale() {
        return cfgScale;
    }

    public void setCfgScale(Integer cfgScale) {
        this.cfgScale = cfgScale;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Boolean getRestoreFaces() {
        return restoreFaces;
    }

    public void setRestoreFaces(Boolean restoreFaces) {
        this.restoreFaces = restoreFaces;
    }

    public String getFaceRestorationModel() {
        return faceRestorationModel;
    }

    public void setFaceRestorationModel(String faceRestorationModel) {
        this.faceRestorationModel = faceRestorationModel;
    }

    public String getSdModelCheckpoint() {
        return sdModelCheckpoint;
    }

    public void setSdModelCheckpoint(String sdModelCheckpoint) {
        this.sdModelCheckpoint = sdModelCheckpoint;
    }

    public Double getDenoisingStrength() {
        return denoisingStrength;
    }

    public void setDenoisingStrength(Double denoisingStrength) {
        this.denoisingStrength = denoisingStrength;
    }
}
