package com.castruche.laboratory_api.fake_profile_api.service;

import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.request.GenerationRequestParameterDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.response.GenerationResponseDto;
import com.castruche.laboratory_api.main_api.service.util.SettingService;
import com.castruche.laboratory_api.main_api.service.util.WebClientService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.buffer.DataBufferLimitException;
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
    public GenerationResponseDto generate(GenerationRequestParameterDto request) {
        request = initSettings();
        String url = this.settingService.getStableDiffusionApiUrl ();
        WebClient webClient = webClientService.initWebClient(url, 16);
        try {
            // Envoi de la requête de manière asynchrone et attente du résultat
            GenerationResponseDto responseDto = webClient.post()
                    .uri("/sdapi/v1/txt2img")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GenerationResponseDto.class)
                    .block();
            if (responseDto != null && responseDto.getImages() != null && responseDto.getImages().length>0) {
                saveImage(responseDto, request.getTemplateTitle());
            } else {
                throw new RuntimeException("Réponse de Stable Diffusion vide ou invalide.");
            }
            return responseDto;
        } catch (DataBufferLimitException e) {
            throw new RuntimeException("Fichier trop volumineux (16mo max) : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération d'image : " + e.getMessage(), e);
        }
    }

    private GenerationRequestParameterDto initSettings(){
        GenerationRequestParameterDto request = new GenerationRequestParameterDto();
        request.setTemplateTitle("Futuristic City at Sunset");
        request.setPrompt("a beautiful futuristic city at sunset, highly detailed, cinematic lighting");
        request.setNegativePrompt("blurry, low resolution, distorted");
        request.setSteps(30);
        request.setWidth(512);
        request.setHeight(512);
        request.setCfgScale(7);
        request.setSeed(-1);
        request.setSamplerIndex("Euler a");
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

    private String manageFolder(String templateTitle){
        String baseFolder = settingService.getSettingValueByShortName("stable_diffusion_output_folder");
        if(baseFolder == null || baseFolder.isEmpty()){
            throw new RuntimeException("Le dossier de sortie n'est pas configuré");
        }
        if(null == templateTitle || templateTitle.isEmpty()){
            templateTitle = "default";
        }
        templateTitle = templateTitle.replaceAll("[^a-zA-Z0-9]", "_").toLowerCase();
        String folder = baseFolder + templateTitle + "\\";
        if (!new File(folder).exists()) {
            new File(folder).mkdirs();
        }

        int index =1;
        String fileName = "generated_image_"+index+".png";

        while(new File(folder+fileName).exists()){
            fileName = "generated_image_"+index+".png";
            index++;
        }

        return folder+fileName;
    }
    
}
