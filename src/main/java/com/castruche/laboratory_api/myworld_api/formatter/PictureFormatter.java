package com.castruche.laboratory_api.myworld_api.formatter;

import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.myworld_api.dto.picture.PictureDto;
import com.castruche.laboratory_api.myworld_api.dto.picture.PictureResizedDto;
import com.castruche.laboratory_api.myworld_api.entity.Picture;
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
        
        return updateEntityFromDto(entity, dto);
    }

    @Override
    public List<PictureResizedDto> entityToLightDto(List<Picture> list) {
        return IFormatter.super.entityToLightDto(list);
    }

    @Override
    public List<PictureDto> entityToDto(List<Picture> list) {
        return IFormatter.super.entityToDto(list);
    }


    public Picture fileToEntity(MultipartFile file, String suffix){
        Picture entity = new Picture();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        String filenameWithoutExtension = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
        String newFileName = filenameWithoutExtension+suffix+extension;

        entity.setFilename(newFileName);
        entity.setExtension(entity.getFilename().substring(entity.getFilename().lastIndexOf(".")));
        entity.setOriginalSize(Double.valueOf(file.getSize()));
        return entity;
    }

    public List<Picture> filesToEntities(List<MultipartFile> files, String suffix){
        List<Picture> entities = new ArrayList<>();
        if(files==null){
            return entities;
        }
        return files.stream().map(file -> fileToEntity(file, suffix)).toList();
    }

    @Override
    public Picture updateEntityFromDto(Picture entity, PictureDto dto) {
        return entity;
    }
}
