package com.castruche.laboratory_api.fake_profile_api.controller;

import com.castruche.laboratory_api.fake_profile_api.dto.ImageTypeDto;
import com.castruche.laboratory_api.fake_profile_api.service.ImageTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.castruche.laboratory_api.fake_profile_api.controller.ConstantUrlFakeProfile.IMAGE_TYPE;

@RestController
@RequestMapping(IMAGE_TYPE)
public class ImageTypeController {

    private final ImageTypeService imageTypeService;
    public ImageTypeController(ImageTypeService imageTypeService) {
        this.imageTypeService = imageTypeService;
    }

    @GetMapping()
    public List<ImageTypeDto> selectAll() {
        return this.imageTypeService.getAllDto();
    }


}
