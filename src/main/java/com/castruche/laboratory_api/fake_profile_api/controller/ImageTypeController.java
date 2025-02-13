package com.castruche.laboratory_api.fake_profile_api.controller;

import com.castruche.laboratory_api.fake_profile_api.dto.ImageTypeDto;
import com.castruche.laboratory_api.fake_profile_api.dto.ModelDto;
import com.castruche.laboratory_api.fake_profile_api.service.ImageTypeService;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping()
    public ImageTypeDto create(@RequestBody ImageTypeDto imageTypeDto) {
        return this.imageTypeService.create(imageTypeDto);
    }

    @PutMapping()
    public ImageTypeDto update(@RequestBody ImageTypeDto imageTypeDto) {
        return this.imageTypeService.update(imageTypeDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.imageTypeService.delete(id);
    }

}
