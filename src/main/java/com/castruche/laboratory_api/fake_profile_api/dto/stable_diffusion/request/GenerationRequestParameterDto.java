package com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.request;

import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.parameter.GenerationExtraParameterDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.parameter.GenerationOverrideSettingParameterDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.parameter.GenerationParameterDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerationRequestParameterDto {

    @JsonIgnore
    private String templateTitle;

    @JsonIgnore
    private Integer total;

    private String prompt;

    @JsonProperty("negative_prompt")
    private String negativePrompt;
    private Long seed;
    private Long subseed;

    @JsonProperty("subseed_strength")
    private int subseedStrength;

    @JsonProperty("sampler_name")
    private String samplerName;

    @JsonProperty("batch_size")
    private int batchSize;
    private int steps;

    @JsonProperty("cfg_scale")
    private int cfgScale;
    private int width;
    private int height;

    @JsonProperty("restore_faces")
    private boolean restoreFaces;

    @JsonProperty("denoising_strength")
    private Double denoisingStrength;

    @JsonProperty("override_settings")
    private GenerationOverrideSettingParameterDto overrideSettings;
  /*  private String refinerCheckpoint;
    private int refinerSwitchAt;*/

    @JsonProperty("face_restoration_model")
    private String faceRestorationModel;

    @JsonProperty("sd_model_checkpoint")
    private String sdModelCheckpoint;

    @JsonProperty("extra_generation_params")
    private GenerationExtraParameterDto extraGenerationParams;


    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public GenerationExtraParameterDto getExtraGenerationParams() {
        return extraGenerationParams;
    }

    public void setExtraGenerationParams(GenerationExtraParameterDto extraGenerationParams) {
        this.extraGenerationParams = extraGenerationParams;
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

    public int getSubseedStrength() {
        return subseedStrength;
    }

    public void setSubseedStrength(int subseedStrength) {
        this.subseedStrength = subseedStrength;
    }

    public String getSamplerName() {
        return samplerName;
    }

    public void setSamplerName(String samplerName) {
        this.samplerName = samplerName;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
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

    public boolean isRestoreFaces() {
        return restoreFaces;
    }

    public void setRestoreFaces(boolean restoreFaces) {
        this.restoreFaces = restoreFaces;
    }

    public Double getDenoisingStrength() {
        return denoisingStrength;
    }

    public void setDenoisingStrength(Double denoisingStrength) {
        this.denoisingStrength = denoisingStrength;
    }

    public GenerationOverrideSettingParameterDto getOverrideSettings() {
        return overrideSettings;
    }

    public void setOverrideSettings(GenerationOverrideSettingParameterDto overrideSettings) {
        this.overrideSettings = overrideSettings;
    }
}
