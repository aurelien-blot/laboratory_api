package com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.parameter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerationExtraParameterDto {

    @JsonProperty("Schedule type")
    private String schedulerType;

    public String getSchedulerType() {
        return schedulerType;
    }

    public void setSchedulerType(String schedulerType) {
        this.schedulerType = schedulerType;
    }
}
