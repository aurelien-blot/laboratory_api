package com.castruche.laboratory_api.fake_profile_api.service;

import com.castruche.laboratory_api.fake_profile_api.dao.ImageTypeRepository;
import com.castruche.laboratory_api.fake_profile_api.dto.ImageTypeDto;
import com.castruche.laboratory_api.fake_profile_api.entity.ImageType;
import com.castruche.laboratory_api.fake_profile_api.formatter.ImageTypeFormatter;
import com.castruche.laboratory_api.main_api.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ImageTypeService extends GenericService<ImageType, ImageTypeDto, ImageTypeDto> {

    private static final Logger logger = LogManager.getLogger(ImageTypeService.class);
    
    public ImageTypeService(ImageTypeRepository imageTypeRepository, ImageTypeFormatter imageTypeFormatter) {
        super(imageTypeRepository, imageTypeFormatter);
    }

}
