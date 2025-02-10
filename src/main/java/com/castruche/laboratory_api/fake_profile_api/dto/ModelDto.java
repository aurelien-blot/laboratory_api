package com.castruche.laboratory_api.fake_profile_api.dto;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class ModelDto extends AbstractDto {

    private String name;
    private String description;
    private String prompt;
    private String negativePrompt;

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
}
