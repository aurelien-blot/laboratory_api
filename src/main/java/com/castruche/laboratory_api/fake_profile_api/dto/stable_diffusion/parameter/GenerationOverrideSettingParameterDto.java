package com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.parameter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerationOverrideSettingParameterDto {

    @JsonProperty("sd_model_checkpoint")
    private String sdModelCheckpoint;

    public String getSdModelCheckpoint() {
        return sdModelCheckpoint;
    }

    public void setSdModelCheckpoint(String sdModelCheckpoint) {
        this.sdModelCheckpoint = sdModelCheckpoint;
    }
}
