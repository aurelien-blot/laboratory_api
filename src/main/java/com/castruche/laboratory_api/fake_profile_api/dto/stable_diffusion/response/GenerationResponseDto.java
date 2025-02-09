package com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.response;

import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.parameter.GenerationParameterDto;
import com.castruche.laboratory_api.fake_profile_api.service.util.CustomGenerationInfoResponseDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class GenerationResponseDto {
    private String[] images;
    private GenerationParameterDto parameters;

    @JsonProperty("info")
    @JsonDeserialize(using = CustomGenerationInfoResponseDeserializer.class)
    private GenerationInfoResponseDto info;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public GenerationParameterDto getParameters() {
        return parameters;
    }

    public void setParameters(GenerationParameterDto parameters) {
        this.parameters = parameters;
    }

    public GenerationInfoResponseDto getInfo() {
        return info;
    }

    public void setInfo(GenerationInfoResponseDto info) {
        this.info = info;
    }
}
