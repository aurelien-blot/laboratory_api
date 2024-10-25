package com.castruche.laboratory_api.map_gen_api.controller.map;

import com.castruche.laboratory_api.map_gen_api.dto.request.SettingsRequestDto;
import com.castruche.laboratory_api.map_gen_api.dto.map.MapDto;
import com.castruche.laboratory_api.map_gen_api.service.map.MapGenerationService;
import com.castruche.laboratory_api.map_gen_api.controller.ConstantUrlMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConstantUrlMap.MAP)
public class MapController {

    private final MapGenerationService mapGenerationService;
    public MapController(MapGenerationService mapGenerationService) {
        this.mapGenerationService = mapGenerationService;
    }

    @PostMapping()
    public MapDto generate(@RequestBody SettingsRequestDto settingsRequestDto) {
        return mapGenerationService.generate(settingsRequestDto);
    }



}
