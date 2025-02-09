package com.castruche.laboratory_api.fake_profile_api.service.util;

import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.response.GenerationInfoResponseDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CustomGenerationInfoResponseDeserializer extends JsonDeserializer<GenerationInfoResponseDto> {
    @Override
    public GenerationInfoResponseDto deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        String jsonString = p.getText();
        return mapper.readValue(jsonString, GenerationInfoResponseDto.class);
    }
}