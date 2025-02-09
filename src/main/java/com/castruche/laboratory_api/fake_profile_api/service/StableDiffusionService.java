package com.castruche.laboratory_api.fake_profile_api.service;

import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.parameter.GenerationExtraParameterDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.parameter.GenerationOverrideSettingParameterDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.request.GenerationRequestParameterDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.response.GenerationResponseDto;
import com.castruche.laboratory_api.main_api.service.util.SettingService;
import com.castruche.laboratory_api.main_api.service.util.WebClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class StableDiffusionService {

    private static final Logger logger = LogManager.getLogger(StableDiffusionService.class);
    private final SettingService settingService;
    private final WebClientService webClientService;
    private final GeneratedImageService generatedImageService;

    public StableDiffusionService(SettingService settingService, WebClientService webClientService, GeneratedImageService generatedImageService) {
        this.settingService = settingService;
        this.webClientService = webClientService;
        this.generatedImageService = generatedImageService;
    }

    @Transactional
    public ResponseEntity<String> generate(GenerationRequestParameterDto request) {
        request = initSettings();
        String url = this.settingService.getStableDiffusionApiUrl();
        WebClient webClient = webClientService.initWebClient(url, 16);
        try {
            //logRequestObject(request);

            for (int i = 0; i < request.getTotal(); i++) {
                // Envoi de la requête de manière asynchrone et attente du résultat
                GenerationResponseDto responseDto = webClient.post()
                        .uri("/sdapi/v1/txt2img")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(GenerationResponseDto.class)
                        .block();
                if (responseDto != null && responseDto.getImages() != null && responseDto.getImages().length > 0) {
                    saveImage(responseDto, request.getTemplateTitle());
                } else {
                    throw new RuntimeException("Réponse de Stable Diffusion vide ou invalide.");
                }
            }
            return ResponseEntity.ok().build();
        } catch (DataBufferLimitException e) {
            return ResponseEntity.internalServerError().body("Fichier trop volumineux (16mo max) : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la génération d'image : " + e.getMessage());
        }
    }

    private void logRequestObject(GenerationRequestParameterDto request){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonRequest = objectMapper.writeValueAsString(request);
            System.out.println("Requête envoyée : " + jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GenerationRequestParameterDto initSettings() {
        GenerationRequestParameterDto request = new GenerationRequestParameterDto();

        /*{
            "prompt": "legs, single person, full body shot, one person only, a young woman, 20 years old, blonde hair, green eyes, casual outfit, smiling, photorealistic, cinematic lighting, realistic skin, natural skin texture, subtle skin pores, soft lighting, photorealistic",
                "negative_prompt": "3D render, CGI, smooth skin, artificial, waxy, doll-like, plastic, multiple people, group photo, portrait",
                "seed": 932980778,
                "subseed": 4223433572,
                "subseed_strength": 0,
                "width": 632,
                "height": 960,
                "sampler_name": "DPM++ 2M",
                "cfg_scale": 1,
                "steps": 56,
                "batch_size": 1,
                "restore_faces": false,
                "face_restoration_model": null,
                "sd_model_checkpoint": "realisticVisionV60B1_v51HyperVAE.safetensors",
                "override_settings": {
            "sd_model_checkpoint": "realisticVisionV60B1_v51HyperVAE.safetensors"
        },
            "denoising_strength": 0.7,
                "extra_generation_params": {
            "Schedule type": "Karras"
        },
            "clip_skip": 1,
                "version": "v1.10.1"
        }*/


        request.setTotal(1);
        request.setTemplateTitle("TestGirl");

        request.setPrompt("legs,single person,full body shot, one person only, a young woman, 20 years old, blonde hair, green eyes, casual outfit, smiling, photorealistic, cinematic lighting, realistic skin, natural skin texture, subtle skin pores, soft lighting, photorealistic");
        request.setNegativePrompt("3D render, CGI, smooth skin, artificial, waxy, doll-like, plastic, multiple people,  group photo, portrait");
        request.setSeed(932980778L);
        request.setSubseed(4223433572L);
        request.setSubseedStrength(0);
        request.setWidth(632);
        request.setHeight(960);
        request.setSamplerName("DPM++ 2M");
        request.setCfgScale(1);
        request.setSteps(56);
        request.setBatchSize(1);
        request.setRestoreFaces(false);
        request.setFaceRestorationModel(null);
        request.setSdModelCheckpoint("realisticVisionV60B1_v51HyperVAE.safetensors");
        GenerationOverrideSettingParameterDto overrideSetting = new GenerationOverrideSettingParameterDto();
        overrideSetting.setSdModelCheckpoint("realisticVisionV60B1_v51HyperVAE.safetensors");
        request.setOverrideSettings(overrideSetting);
        request.setDenoisingStrength(0.7);
        GenerationExtraParameterDto extraParameter = new GenerationExtraParameterDto();
        extraParameter.setSchedulerType("Karras");
        request.setExtraGenerationParams(extraParameter);

        //request.setRefinerCheckpoint("v1-5-pruned-emaonly.safetensors [6ce0161689]");
        //request.setRefinerSwitchAt(1);


        return request;
    }

    private void saveImage(GenerationResponseDto response, String templateTitle) {
        try {

            String filePath = manageFolder(templateTitle);
            byte[] imageBytes = Base64.getDecoder().decode(response.getImages()[0]);

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(imageBytes);
            }
            this.generatedImageService.createFromApi(response, filePath, templateTitle);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'image : " + e.getMessage(), e);
        }
    }

    private String manageFolder(String templateTitle) {
        String baseFolder = settingService.getSettingValueByShortName("stable_diffusion_output_folder");
        if (baseFolder == null || baseFolder.isEmpty()) {
            throw new RuntimeException("Le dossier de sortie n'est pas configuré");
        }
        if (null == templateTitle || templateTitle.isEmpty()) {
            templateTitle = "default";
        }
        templateTitle = templateTitle.replaceAll("[^a-zA-Z0-9]", "_").toLowerCase();
        String folder = baseFolder + templateTitle + "\\";
        if (!new File(folder).exists()) {
            new File(folder).mkdirs();
        }

        int index = 1;
        String fileName = "generated_image_" + index + ".png";

        while (new File(folder + fileName).exists()) {
            fileName = "generated_image_" + index + ".png";
            index++;
        }

        return folder + fileName;
    }

}
