package com.castruche.laboratory_api.map_gen_api.controller.map;


import com.castruche.laboratory_api.map_gen_api.controller.ConstantUrlMap;
import com.castruche.laboratory_api.map_gen_api.dto.map.BiomeLightDto;
import com.castruche.laboratory_api.map_gen_api.service.map.BiomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(ConstantUrlMap.BIOME)
public class BiomeController {

    private final BiomeService biomeService;
    public BiomeController(BiomeService biomeService) {
        this.biomeService = biomeService;
    }

    @GetMapping()
    public List<BiomeLightDto> selectAll() {
        return biomeService.getAllDtoLight();
    }



}
