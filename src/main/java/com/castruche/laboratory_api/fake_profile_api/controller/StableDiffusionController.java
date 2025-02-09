package com.castruche.laboratory_api.fake_profile_api.controller;

import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.request.GenerationRequestParameterDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.response.GenerationResponseDto;
import com.castruche.laboratory_api.fake_profile_api.service.StableDiffusionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.castruche.laboratory_api.fake_profile_api.controller.ConstantUrlFakeProfile.STABLE_DIFFUSION;

@RestController
@RequestMapping(STABLE_DIFFUSION)
public class StableDiffusionController {


    private final StableDiffusionService stableDiffusionService;

    public StableDiffusionController(StableDiffusionService stableDiffusionService) {
        this.stableDiffusionService = stableDiffusionService;
    }

    @PostMapping()
    public ResponseEntity<String> generatePicture(@RequestBody GenerationRequestParameterDto request) {
        return this.stableDiffusionService.generate(request);
    }

}
