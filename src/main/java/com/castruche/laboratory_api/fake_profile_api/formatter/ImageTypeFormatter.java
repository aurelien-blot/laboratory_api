package com.castruche.laboratory_api.fake_profile_api.formatter;

import com.castruche.laboratory_api.fake_profile_api.dto.ImageTypeDto;
import com.castruche.laboratory_api.fake_profile_api.entity.ImageType;
import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageTypeFormatter implements IFormatter<ImageType, ImageTypeDto, ImageTypeDto> {

    @Override
    public ImageTypeDto entityToDto(ImageType entity) {
        if(entity == null){
            return null;
        }
        ImageTypeDto imageTypeDto = new ImageTypeDto();
        imageTypeDto.setId(entity.getId());
        imageTypeDto.setCreationTime(entity.getCreationTime());
        imageTypeDto.setModificationTime(entity.getModificationTime());
        imageTypeDto.setName(entity.getName());
        imageTypeDto.setDescription(entity.getDescription());
        imageTypeDto.setSeed(entity.getSeed());
        imageTypeDto.setSubseed(entity.getSubseed());
        imageTypeDto.setWidth(entity.getWidth());
        imageTypeDto.setHeight(entity.getHeight());
        imageTypeDto.setSamplerName(entity.getSamplerName());
        imageTypeDto.setCfgScale(entity.getCfgScale());
        imageTypeDto.setSteps(entity.getSteps());
        imageTypeDto.setRestoreFaces(entity.getRestoreFaces());
        imageTypeDto.setFaceRestorationModel(entity.getFaceRestorationModel());
        imageTypeDto.setSdModelCheckpoint(entity.getSdModelCheckpoint());
        imageTypeDto.setDenoisingStrength(entity.getDenoisingStrength());

        return imageTypeDto;
    }

    @Override
    public ImageTypeDto entityToLightDto(ImageType imageType) {
        return this.entityToDto(imageType);
    }

    @Override
    public ImageType dtoToEntity(ImageTypeDto dto) {
        ImageType imageType = new ImageType();
        imageType.setId(dto.getId());
        return imageType;
    }

    @Override
    public List<ImageTypeDto> entityToDto(List<ImageType> imageTypes) {
        return IFormatter.super.entityToDto(imageTypes);
    }

    @Override
    public List<ImageTypeDto> entityToLightDto(List<ImageType> imageTypes) {
        return IFormatter.super.entityToLightDto(imageTypes);
    }

}
