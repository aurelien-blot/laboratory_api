package com.castruche.laboratory_api.map_gen_api.service.map;

import com.castruche.laboratory_api.main_api.service.GenericService;
import com.castruche.laboratory_api.map_gen_api.dao.MapRepository;
import com.castruche.laboratory_api.map_gen_api.dto.map.MapDto;
import com.castruche.laboratory_api.map_gen_api.entity.map.Map;
import com.castruche.laboratory_api.map_gen_api.formatter.map.MapFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MapService extends GenericService<Map, MapDto, MapDto> {

    private static final Logger logger = LogManager.getLogger(MapService.class);

    public MapService(MapRepository mapRepository, MapFormatter mapFormatter) {
        super(mapRepository, mapFormatter);
    }
    
}
