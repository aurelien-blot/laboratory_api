package com.castruche.laboratory_api.fake_profile_api.formatter;

import com.castruche.laboratory_api.fake_profile_api.dao.ModelRepository;
import com.castruche.laboratory_api.fake_profile_api.dto.ModelDto;
import com.castruche.laboratory_api.fake_profile_api.entity.Model;
import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelFormatter implements IFormatter<Model, ModelDto, ModelDto> {

    private final ModelRepository modelRepository;

    public ModelFormatter(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public ModelDto entityToDto(Model entity) {
        if(entity == null){
            return null;
        }
        ModelDto modelDto = new ModelDto();
        modelDto.setId(entity.getId());
        modelDto.setCreationTime(entity.getCreationTime());
        modelDto.setModificationTime(entity.getModificationTime());
        modelDto.setPrompt(entity.getPrompt());
        modelDto.setNegativePrompt(entity.getNegativePrompt());
        modelDto.setDescription(entity.getDescription());
        modelDto.setName(entity.getName());

        return modelDto;
    }

    @Override
    public ModelDto entityToLightDto(Model model) {
        return this.entityToDto(model);
    }

    @Override
    public Model dtoToEntity(ModelDto dto) {
        Model model ;
        if(null!=dto.getId()){
            model = modelRepository.findById(dto.getId()).orElse(null);
        }
        else{
            model = new Model();
        }
        assert model != null;
        model.setPrompt(dto.getPrompt());
        model.setNegativePrompt(dto.getNegativePrompt());
        model.setDescription(dto.getDescription());
        model.setName(dto.getName());
        return model;
    }

    @Override
    public List<ModelDto> entityToDto(List<Model> models) {
        return IFormatter.super.entityToDto(models);
    }

    @Override
    public List<ModelDto> entityToLightDto(List<Model> models) {
        return IFormatter.super.entityToLightDto(models);
    }

}
