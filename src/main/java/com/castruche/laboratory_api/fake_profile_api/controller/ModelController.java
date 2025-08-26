package com.castruche.laboratory_api.fake_profile_api.controller;

import com.castruche.laboratory_api.fake_profile_api.dto.ModelDto;
import com.castruche.laboratory_api.fake_profile_api.service.ModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping()
    public ModelDto create(@RequestBody ModelDto modelDto) {
        return this.modelService.create(modelDto);
    }

    @PutMapping()
    public ModelDto update(@RequestBody ModelDto modelDto) {
        return this.modelService.update(modelDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.modelService.delete(id);
    }

}
