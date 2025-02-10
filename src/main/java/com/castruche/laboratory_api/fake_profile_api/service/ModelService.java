package com.castruche.laboratory_api.fake_profile_api.service;

import com.castruche.laboratory_api.fake_profile_api.dao.ModelRepository;
import com.castruche.laboratory_api.fake_profile_api.dto.ModelDto;
import com.castruche.laboratory_api.fake_profile_api.entity.Model;
import com.castruche.laboratory_api.fake_profile_api.formatter.ModelFormatter;
import com.castruche.laboratory_api.main_api.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ModelService extends GenericService<Model, ModelDto, ModelDto> {

    private static final Logger logger = LogManager.getLogger(ModelService.class);

    private final ModelRepository modelRepository;
    private final ModelFormatter modelFormatter;
    public ModelService(ModelRepository modelRepository, ModelFormatter modelFormatter) {
        super(modelRepository, modelFormatter);
        this.modelRepository = modelRepository;
        this.modelFormatter = modelFormatter;
    }

}
