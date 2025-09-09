package com.castruche.laboratory_api.myworld_api.formatter;

import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.myworld_api.dto.picture.PictureDto;
import com.castruche.laboratory_api.myworld_api.dto.picture.PictureResizedDto;
import com.castruche.laboratory_api.myworld_api.entity.Picture;
import com.castruche.laboratory_api.myworld_api.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureFormatter implements IFormatter<Picture, PictureDto, PictureResizedDto> {

    @Override
    public PictureDto entityToDto(Picture entity) {
        if(entity == null){
            return null;
        }
        PictureDto dto = new PictureDto();
        dto.setId(entity.getId());
        dto.setCreationTime(entity.getCreationTime());
        dto.setModificationTime(entity.getModificationTime());
        dto.setFilename(entity.getFilename());
        return dto;
    }

    @Override
    public PictureResizedDto entityToLightDto(Picture entity) {
        if(entity == null){
            return null;
        }
        PictureResizedDto dto = new PictureResizedDto();
        dto.setId(entity.getId());
        dto.setCreationTime(entity.getCreationTime());
        dto.setModificationTime(entity.getModificationTime());
        dto.setFilename(entity.getFilename());
        dto.setFilePath(entity.getResizedFileFilepath());
        return dto;
    }

    @Override
    public Picture dtoToEntity(PictureDto dto) {
        Picture entity = new Picture();
        entity.setId(dto.getId());
        entity.setCreationTime(dto.getCreationTime());
        entity.setModificationTime(dto.getModificationTime());
        
        return entity;
    }

    @Override
    public List<PictureResizedDto> entityToLightDto(List<Picture> list) {
        return IFormatter.super.entityToLightDto(list);
    }

    @Override
    public List<PictureDto> entityToDto(List<Picture> list) {
        return IFormatter.super.entityToDto(list);
    }


    public Picture fileToEntity(MultipartFile file){
        Picture entity = new Picture();
        entity.setFilename(file.getOriginalFilename());
        entity.setExtension(entity.getFilename().substring(entity.getFilename().lastIndexOf(".")));
        entity.setOriginalSize(Double.valueOf(file.getSize()));
        return entity;
    }

    public List<Picture> filesToEntities(List<MultipartFile> files){
        List<Picture> entities = new ArrayList<>();
        if(files==null){
            return entities;
        }
        return files.stream().map(this::fileToEntity).toList();
    }
}
