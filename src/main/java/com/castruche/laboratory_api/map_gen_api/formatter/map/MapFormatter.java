package com.castruche.laboratory_api.map_gen_api.formatter.map;

import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.map_gen_api.dto.map.MapDto;
import com.castruche.laboratory_api.map_gen_api.entity.map.Map;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapFormatter implements IFormatter<Map, MapDto, MapDto> {

    @Override
    public MapDto entityToDto(Map entity) {
        if(entity == null){
            return null;
        }
        MapDto mapDto = new MapDto();
        mapDto.setId(entity.getId());
        
        return mapDto;
    }

    @Override
    public MapDto entityToLightDto(Map map) {
        return null;
    }

    @Override
    public Map dtoToEntity(MapDto dto) {
        Map map = new Map();
        map.setId(dto.getId());
        return map;
    }

    @Override
    public List<MapDto> entityToDto(List<Map> maps) {
        return IFormatter.super.entityToDto(maps);
    }

    @Override
    public List<MapDto> entityToLightDto(List<Map> maps) {
        return IFormatter.super.entityToLightDto(maps);
    }

}
