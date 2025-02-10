package com.castruche.laboratory_api.fake_profile_api.controller;

import com.castruche.laboratory_api.fake_profile_api.dto.ModelDto;
import com.castruche.laboratory_api.fake_profile_api.service.ModelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.castruche.laboratory_api.fake_profile_api.controller.ConstantUrlFakeProfile.IMAGE_TYPE;
import static com.castruche.laboratory_api.fake_profile_api.controller.ConstantUrlFakeProfile.MODEL;

@RestController
@RequestMapping(MODEL)
public class ModelController {

    private final ModelService modelService;
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping()
    public List<ModelDto> selectAll() {
        return this.modelService.getAllDto();
    }


}
