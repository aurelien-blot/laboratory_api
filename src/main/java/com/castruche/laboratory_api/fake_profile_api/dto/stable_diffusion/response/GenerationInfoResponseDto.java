package com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerationInfoResponseDto {

    /*{
        "prompt":"a beautiful futuristic city at sunset, highly detailed, cinematic lighting", "all_prompts": [
        "a beautiful futuristic city at sunset, highly detailed, cinematic lighting"],"negative_prompt":
        "", "all_negative_prompts": [""],"seed":1010752383, "all_seeds": [1010752383],"subseed":0, "all_subseeds": [0],
        "subseed_strength":0, "width":512, "height":512, "sampler_name":"Euler", "cfg_scale":7.0, "steps":
        30, "batch_size":1, "restore_faces":false, "face_restoration_model":null, "sd_model_name":
        "realisticVisionV60B1_v51HyperVAE", "sd_model_hash":"f47e942ad4", "sd_vae_name":null, "sd_vae_hash":
        null, "seed_resize_from_w":-1, "seed_resize_from_h":-1, "denoising_strength":null, "extra_generation_params":{
    },"index_of_first_image":0, "infotexts": [
        "a beautiful futuristic city at sunset, highly detailed, cinematic lighting\nSteps: 30, Sampler: Euler, Schedule type: Automatic, CFG scale: 7.0, Seed: 1010752383, Size: 512x512, Model hash: f47e942ad4, Model: realisticVisionV60B1_v51HyperVAE, Version: v1.10.1"],
        "styles": [],"job_timestamp":"20250209181148", "clip_skip":1, "is_using_inpainting_conditioning":
        false, "version":"v1.10.1"
    }*/

    @JsonProperty("all_seeds")
    private Long[] allSeeds;

    @JsonProperty("all_subseeds")
    private Long[] allSubseeds;

    public Long[] getAllSeeds() {
        return allSeeds;
    }

    public void setAllSeeds(Long[] allSeeds) {
        this.allSeeds = allSeeds;
    }

    public Long[] getAllSubseeds() {
        return allSubseeds;
    }

    public void setAllSubseeds(Long[] allSubseeds) {
        this.allSubseeds = allSubseeds;
    }
}
