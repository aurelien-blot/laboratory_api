package com.castruche.laboratory_api.main_api.formatter;

import java.util.ArrayList;
import java.util.List;

public interface IFormatter<ENTITY, DTO, LIGHT_DTO> {

    DTO entityToDto(ENTITY entity);
    LIGHT_DTO entityToLightDto(ENTITY entity);
    ENTITY dtoToEntity(DTO dto);
    default List<DTO> entityToDto(List<ENTITY> entityList) {
        List<DTO> results= new ArrayList<>();
        if(entityList == null || entityList.isEmpty()){
            return results;
        }
        entityList.forEach(entity -> results.add(entityToDto(entity)));
        return results;
    }
    default List<LIGHT_DTO> entityToLightDto(List<ENTITY> entityList) {
        List<LIGHT_DTO> results= new ArrayList<>();
        if(entityList == null || entityList.isEmpty()){
            return results;
        }
        entityList.forEach(entity -> results.add(entityToLightDto(entity)));
        return results;
    }

    ENTITY updateEntityFromDto(ENTITY entity, DTO dto);

}
