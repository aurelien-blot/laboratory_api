package com.castruche.laboratory_api.fake_profile_api.formatter;

import com.castruche.laboratory_api.fake_profile_api.dto.GeneratedImageDto;
import com.castruche.laboratory_api.fake_profile_api.entity.GeneratedImage;
import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedImageFormatter implements IFormatter<GeneratedImage, GeneratedImageDto, GeneratedImageDto> {

    @Override
    public GeneratedImageDto entityToDto(GeneratedImage entity) {
        if(entity == null){
            return null;
        }
        GeneratedImageDto generatedImageDto = new GeneratedImageDto();
        generatedImageDto.setId(entity.getId());
        generatedImageDto.setCreationTime(entity.getCreationTime());
        generatedImageDto.setModificationTime(entity.getModificationTime());
        generatedImageDto.setTemplateTitle(entity.getTemplateTitle());
        generatedImageDto.setSeedUsed(entity.getSeedUsed());
        generatedImageDto.setSubseedUsed(entity.getSubseedUsed());
        generatedImageDto.setFilePath(entity.getFilePath());
        generatedImageDto.setPrompt(entity.getPrompt());
        generatedImageDto.setNegativePrompt(entity.getNegativePrompt());
        generatedImageDto.setCfgScale(entity.getCfgScale());
        generatedImageDto.setWidth(entity.getWidth());
        generatedImageDto.setHeight(entity.getHeight());
        generatedImageDto.setDenoisingStrength(entity.getDenoisingStrength());
        generatedImageDto.setRefinerCheckpoint(entity.getRefinerCheckpoint());
        generatedImageDto.setRefinerSwitchAt(entity.getRefinerSwitchAt());
        generatedImageDto.setSendImages(entity.isSendImages());

        return generatedImageDto;
    }

    @Override
    public GeneratedImageDto entityToLightDto(GeneratedImage generatedImage) {
        return this.entityToDto(generatedImage);
    }

    @Override
    public GeneratedImage dtoToEntity(GeneratedImageDto dto) {
        GeneratedImage generatedImage = new GeneratedImage();
        generatedImage.setId(dto.getId());
        return generatedImage;
    }

    @Override
    public List<GeneratedImageDto> entityToDto(List<GeneratedImage> generatedImages) {
        return IFormatter.super.entityToDto(generatedImages);
    }

    @Override
    public List<GeneratedImageDto> entityToLightDto(List<GeneratedImage> generatedImages) {
        return IFormatter.super.entityToLightDto(generatedImages);
    }

}
