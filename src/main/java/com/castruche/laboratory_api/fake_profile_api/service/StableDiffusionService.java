package com.castruche.laboratory_api.fake_profile_api.service;

import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.request.GenerationRequestParameterDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.response.GenerationResponseDto;
import com.castruche.laboratory_api.main_api.service.util.SettingService;
import com.castruche.laboratory_api.main_api.service.util.WebClientService;
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
    public ResponseEntity<String> generateTxtToImages(GenerationRequestParameterDto request, String templateTitle) {
        request.setTemplateTitle(templateTitle);
        String url = this.settingService.getStableDiffusionApiUrl();
        WebClient webClient = webClientService.initWebClient(url, 16);
        try {

            for(int i=0; i<request.getBatchCount();i++){
                GenerationResponseDto responseDto = webClient.post()
                        .uri("/sdapi/v1/txt2img")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(GenerationResponseDto.class)
                        .block();
                if (responseDto != null && responseDto.getImages() != null && responseDto.getImages().length > 0) {
                    saveImageList(responseDto, request.getTemplateTitle());
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


    private void saveImageList(GenerationResponseDto response, String templateTitle) {

        for (int i = 0; i < response.getImages().length; i++) {
            String filePath = manageFolder(templateTitle);
            String image = response.getImages()[i];
            saveImage(response, filePath, templateTitle, image, i);
        }
    }

    private void saveImage(GenerationResponseDto response, String filePath, String templateTitle, String image, int index) {
        try {

            byte[] imageBytes = Base64.getDecoder().decode(image);

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(imageBytes);
            }
            this.generatedImageService.createFromApi(response, filePath, templateTitle, index);
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
