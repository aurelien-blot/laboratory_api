package com.castruche.laboratory_api.fake_profile_api.controller;

import com.castruche.laboratory_api.fake_profile_api.dto.GeneratedImageDto;
import com.castruche.laboratory_api.fake_profile_api.dto.PicturePreviewDto;
import com.castruche.laboratory_api.fake_profile_api.service.GeneratedImageService;
import com.castruche.laboratory_api.map_gen_api.controller.ConstantUrlMap;
import com.castruche.laboratory_api.map_gen_api.dto.map.MapDto;
import com.castruche.laboratory_api.map_gen_api.dto.request.SettingsRequestDto;
import com.castruche.laboratory_api.map_gen_api.service.map.MapGenerationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.castruche.laboratory_api.fake_profile_api.controller.ConstantUrlFakeProfile.GENERATED_IMAGES;

@RestController
@RequestMapping(GENERATED_IMAGES)
public class GeneratedImageController {

    private final GeneratedImageService generatedImageService;
    public GeneratedImageController(GeneratedImageService generatedImageService) {
        this.generatedImageService = generatedImageService;
    }

    @GetMapping()
    public List<GeneratedImageDto> selectAll(@RequestParam(required = false) String templateTitle) {
        return this.generatedImageService.selectAll(templateTitle);
    }

    @GetMapping("/load/{id}")
    public PicturePreviewDto selectAll(@PathVariable Long id) {
        return this.generatedImageService.loadPicture(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.generatedImageService.completeDelete(id);
    }


}
