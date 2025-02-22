package com.castruche.laboratory_api.fake_profile_api.service;

import com.castruche.laboratory_api.fake_profile_api.dao.GeneratedImageRepository;
import com.castruche.laboratory_api.fake_profile_api.dto.GeneratedImageDto;
import com.castruche.laboratory_api.fake_profile_api.dto.PicturePreviewDto;
import com.castruche.laboratory_api.fake_profile_api.dto.stable_diffusion.response.GenerationResponseDto;
import com.castruche.laboratory_api.fake_profile_api.entity.GeneratedImage;
import com.castruche.laboratory_api.fake_profile_api.formatter.GeneratedImageFormatter;
import com.castruche.laboratory_api.main_api.service.GenericService;
import com.castruche.laboratory_api.map_gen_api.dao.MapRepository;
import com.castruche.laboratory_api.map_gen_api.dto.map.MapDto;
import com.castruche.laboratory_api.map_gen_api.entity.map.Map;
import com.castruche.laboratory_api.map_gen_api.formatter.map.MapFormatter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Service
public class GeneratedImageService extends GenericService<GeneratedImage, GeneratedImageDto, GeneratedImageDto> {

    private static final Logger logger = LogManager.getLogger(GeneratedImageService.class);

    private final GeneratedImageRepository generatedImageRepository;
    private final GeneratedImageFormatter generatedImageFormatter;

    public GeneratedImageService(GeneratedImageRepository generatedImageRepository, GeneratedImageFormatter generatedImageFormatter) {
        super(generatedImageRepository, generatedImageFormatter);
        this.generatedImageRepository = generatedImageRepository;
        this.generatedImageFormatter = generatedImageFormatter;
    }


    @Transactional
    public GeneratedImageDto createFromApi(GenerationResponseDto response, String filePath, String templateTitle, int index) {
        if (response == null) {
            throw new RuntimeException("Réponse de Stable Diffusion vide ou invalide");
        }
        if (filePath == null) {
            throw new RuntimeException("Chemin du fichier vide ou invalide");
        }

        GeneratedImage generatedImage;
        try {
            generatedImage = new GeneratedImage();

            generatedImage.setFilePath(filePath);
            generatedImage.setTemplateTitle(templateTitle);
            if (null != response.getInfo()) {
                generatedImage.setSeedUsed(response.getInfo().getAllSeeds()[index]);
                generatedImage.setSubseedUsed(response.getInfo().getAllSubseeds()[index]);
            }
            if (null != response.getParameters()) {

                generatedImage.setPrompt(response.getParameters().getPrompt());
                generatedImage.setNegativePrompt(response.getParameters().getNegativePrompt());
                if (null != response.getParameters().getStyles()) {

                    generatedImage.setStyles(response.getParameters().getStyles().toString());
                }
                generatedImage.setSubseedStrength(response.getParameters().getSubseedStrength());
                generatedImage.setSeedResizeFromH(response.getParameters().getSeedResizeFromH());
                generatedImage.setSeedResizeFromW(response.getParameters().getSeedResizeFromW());
                generatedImage.setSamplerName(response.getParameters().getSamplerName());
                generatedImage.setScheduler(response.getParameters().getScheduler());
                generatedImage.setBatchSize(response.getParameters().getBatchSize());
                generatedImage.setnIter(response.getParameters().getnIter());
                generatedImage.setSteps(response.getParameters().getSteps());
                generatedImage.setCfgScale(response.getParameters().getCfgScale());
                generatedImage.setWidth(response.getParameters().getWidth());
                generatedImage.setHeight(response.getParameters().getHeight());
                generatedImage.setRestoreFaces(response.getParameters().isRestoreFaces());
                generatedImage.setTiling(response.getParameters().isTiling());
                generatedImage.setDoNotSaveSamples(response.getParameters().isDoNotSaveSamples());
                generatedImage.setDoNotSaveGrid(response.getParameters().isDoNotSaveGrid());
                generatedImage.setEta(response.getParameters().getEta());
                generatedImage.setDenoisingStrength(response.getParameters().getDenoisingStrength());
                generatedImage.setsMinUncond(response.getParameters().getsMinUncond());
                generatedImage.setsChurn(response.getParameters().getsChurn());
                generatedImage.setsTmax(response.getParameters().getsTmax());
                generatedImage.setsTmin(response.getParameters().getsTmin());
                generatedImage.setsNoise(response.getParameters().getsNoise());
                generatedImage.setOverrideSettingsRestoreAfterwards(response.getParameters().isOverrideSettingsRestoreAfterwards());
                generatedImage.setRefinerCheckpoint(response.getParameters().getRefinerCheckpoint());
                generatedImage.setRefinerSwitchAt(response.getParameters().getRefinerSwitchAt());
                generatedImage.setDisableExtraNetworks(response.getParameters().isDisableExtraNetworks());
                generatedImage.setFirstpassImage(response.getParameters().getFirstpassImage());
                generatedImage.setEnableHr(response.getParameters().isEnableHr());
                generatedImage.setFirstphaseWidth(response.getParameters().getFirstphaseWidth());
                generatedImage.setFirstphaseHeight(response.getParameters().getFirstphaseHeight());
                generatedImage.setHrScale(response.getParameters().getHrScale());
                generatedImage.setHrUpscaler(response.getParameters().getHrUpscaler());
                generatedImage.setHrSecondPassSteps(response.getParameters().getHrSecondPassSteps());
                generatedImage.setHrResizeX(response.getParameters().getHrResizeX());
                generatedImage.setHrResizeY(response.getParameters().getHrResizeY());
                generatedImage.setHrCheckpointName(response.getParameters().getHrCheckpointName());
                generatedImage.setHrSamplerName(response.getParameters().getHrSamplerName());
                generatedImage.setHrScheduler(response.getParameters().getHrScheduler());
                generatedImage.setHrPrompt(response.getParameters().getHrPrompt());
                generatedImage.setHrNegativePrompt(response.getParameters().getHrNegativePrompt());
                generatedImage.setForceTaskId(response.getParameters().getForceTaskId());
                generatedImage.setSamplerIndex(response.getParameters().getSamplerIndex());
                generatedImage.setScriptName(response.getParameters().getScriptName());
                if (null != response.getParameters().getScriptArgs()) {
                    generatedImage.setScriptArgs(response.getParameters().getScriptArgs().toString());
                }
                generatedImage.setSendImages(response.getParameters().isSendImages());
                generatedImage.setSaveImages(response.getParameters().isSaveImages());
                generatedImage.setInfotext(response.getParameters().getInfotext());

            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'image générée", e);
        }

        generatedImage = this.generatedImageRepository.save(generatedImage);
        return this.generatedImageFormatter.entityToDto(generatedImage);
    }

    public List<GeneratedImageDto> selectAll(String templateTitle) {
        List<GeneratedImage> generatedImages;
        if (templateTitle == null) {
            generatedImages = this.generatedImageRepository.findAll();
        } else {
            generatedImages = this.generatedImageRepository.findByTemplateTitle(templateTitle);
        }
        return this.generatedImageFormatter.entityToLightDto(generatedImages);
    }

    public PicturePreviewDto loadPicture(Long id) {
        if (null == id) {
            throw new RuntimeException("Id de l'image non fourni");
        }
        GeneratedImage generatedImage = this.generatedImageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image non trouvée"));

        String imagePath = generatedImage.getFilePath();

        File imageFile = new File(imagePath);
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(imageFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture de l'image", e);
        }
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        PicturePreviewDto imageDto = new PicturePreviewDto();
        imageDto.setBase64Image(base64Image);

        return imageDto;
    }

    @Transactional
    public void completeDelete(Long id) {
        if (null == id) {
            throw new RuntimeException("Id de l'image non fourni");
        }
        GeneratedImage generatedImage = this.generatedImageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image non trouvée"));

        String imagePath = generatedImage.getFilePath();

        File imageFile = new File(imagePath);
        if (!imageFile.delete()) {
            throw new RuntimeException("Erreur lors de la suppression de l'image");
        }

        this.generatedImageRepository.delete(generatedImage);
    }
}
