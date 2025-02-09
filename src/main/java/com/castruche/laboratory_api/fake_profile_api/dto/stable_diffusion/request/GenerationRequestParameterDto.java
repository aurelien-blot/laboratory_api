package com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.request;

import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.GenerationParameterDto;

public class GenerationRequestParameterDto extends GenerationParameterDto {
    private String templateTitle;

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }
}
