package com.castruche.laboratory_api.map_gen_api.service.map;

import com.castruche.laboratory_api.main_api.dto.util.noise.PerlinNoise;
import com.castruche.laboratory_api.map_gen_api.dto.request.SettingsRequestDto;
import com.castruche.laboratory_api.map_gen_api.dto.map.BiomeDto;
import com.castruche.laboratory_api.map_gen_api.dto.map.MapDto;
import com.castruche.laboratory_api.map_gen_api.dto.request.SettingsBiomeRequestDto;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapGenerationService  {

    private static final Logger logger = LogManager.getLogger(MapGenerationService.class);

    private final BiomeService biomeService;

    public MapGenerationService(BiomeService biomeService) {
        this.biomeService = biomeService;
    }

    @Transactional
    public MapDto generate(SettingsRequestDto settingsRequestDto) {
        MapDto map = new MapDto();
        map.setHeightPx(settingsRequestDto.getHeightPx());
        map.setWidthPx(settingsRequestDto.getWidthPx());
        map.setMap(fillMapWithBiomes(settingsRequestDto));
        return map;
    }

    private BiomeDto [][] fillMapWithBiomes(SettingsRequestDto settingsRequestDto) {
        if(settingsRequestDto==null || !settingsRequestDto.isValid()){
            throw new IllegalArgumentException("Invalid settings");
        }
        checkBiomeListValidity(settingsRequestDto.getSettingsBiomeList());

        List<Long> biomesId =  settingsRequestDto.getSettingsBiomeList().stream().map(SettingsBiomeRequestDto::getBiomeId).toList();
        List<BiomeDto> biomeList = biomeService.selectDtoByIdIn(biomesId);
        HashMap<Long, BiomeDto> biomeMap = biomeList.stream().collect(HashMap::new, (m, b) -> m.put(b.getId(), b), HashMap::putAll);

        //BiomeDto [][] map =randomBiomeGeneration(settingsRequestDto, biomeMap);

        return perlinBiomeGeneration(settingsRequestDto, biomeMap);
    }

    private void checkBiomeListValidity(List<SettingsBiomeRequestDto> settingsBiomeList) {
        if(settingsBiomeList == null || settingsBiomeList.isEmpty()){
            throw new IllegalArgumentException("Liste des biomes vide");
        }
        if(settingsBiomeList.stream().anyMatch(settingsBiomeRequestDto -> settingsBiomeRequestDto.getBiomeId() == null || settingsBiomeRequestDto.getPercentage() == null)){
            throw new IllegalArgumentException("Liste des biomes invalide");
        }
        if(settingsBiomeList.stream().map(SettingsBiomeRequestDto::getPercentage).mapToDouble(Double::doubleValue).sum() > 100){
            throw new IllegalArgumentException("Liste des biomes > 100%");
        }
    }
    private void fillEmptyPixels(Long totalPixels, List<BiomeDto> biomePixelList) {
        if(biomePixelList.size() < totalPixels){
            BiomeDto emptyBiome = biomeService.findByTechnicalName("ocean");
            int emptyPixels = Math.toIntExact(totalPixels - biomePixelList.size());
            for (int i = 0; i < emptyPixels; i++) biomePixelList.add(emptyBiome);
        }
    }

    private void fillEmptyPixels(Double totalPercentCovered,  List<SettingsBiomeRequestDto> settingsBiomeList) {
        if(100-totalPercentCovered > 0){
            BiomeDto emptyBiome = biomeService.findByTechnicalName("ocean");
            SettingsBiomeRequestDto settingsBiomeRequestDto = new SettingsBiomeRequestDto();
            settingsBiomeRequestDto.setBiomeId(emptyBiome.getId());
            settingsBiomeRequestDto.setTreshold(100-totalPercentCovered);
            settingsBiomeList.add(settingsBiomeRequestDto);
        }
    }

    private  BiomeDto [][] randomBiomeGeneration(SettingsRequestDto settingsRequestDto, HashMap<Long, BiomeDto> biomeMap){
        List<BiomeDto> biomePixelList = new ArrayList<>();
        settingsRequestDto.getSettingsBiomeList().forEach(settingsBiomeRequestDto -> {
            if(settingsBiomeRequestDto.getBiomeId()==null || settingsBiomeRequestDto.getPercentage() == null || settingsBiomeRequestDto.getPercentage() <=0){
                return;
            }
            BiomeDto biome = biomeMap.get(settingsBiomeRequestDto.getBiomeId());
            settingsBiomeRequestDto.setPixelTotal(Math.round(settingsRequestDto.getTotalPixels() * settingsBiomeRequestDto.getPercentage() / 100));
            for (int i = 0; i < settingsBiomeRequestDto.getPixelTotal(); i++) biomePixelList.add(biome);
        });

        fillEmptyPixels(settingsRequestDto.getTotalPixels(), biomePixelList);

        Collections.shuffle(biomePixelList);
        BiomeDto [][] map= new BiomeDto[settingsRequestDto.getHeightPx().intValue()][settingsRequestDto.getWidthPx().intValue()];

        int index = 0;
        for (int y = 0; y < settingsRequestDto.getWidthPx(); y++) {
            for (int x = 0; x < settingsRequestDto.getHeightPx(); x++) {
                map[x][y] = biomePixelList.get(index++);
            }
        }
        return map;
    }

    private BiomeDto [][] perlinBiomeGeneration(SettingsRequestDto settingsRequestDto, HashMap<Long, BiomeDto> biomeMap){
        int width = settingsRequestDto.getWidthPx().intValue();
        int height = settingsRequestDto.getHeightPx().intValue();

        double scale = 0.01; // Ajuste ce paramètre pour changer la taille des biomes
        long seed = new Random().nextLong();

        // Générer la carte de bruit
        PerlinNoise noise = new PerlinNoise(seed);
        double[][] noiseMap = new double[width][height];
        List<Double> values = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.noise(x * scale, y * scale);
                value = (value + 1) / 2; // Normaliser entre 0 et 1
                noiseMap[x][y] = value;
                values.add(value);
            }
        }

        // Trier les valeurs de bruit
        Collections.sort(values);

        // Déterminer les seuils
        int totalPixels = width * height;

        double totalPercentCovered = 0;
        List<SettingsBiomeRequestDto> settingsBiomeList = new ArrayList<>();
        for(SettingsBiomeRequestDto settingsBiomeRequestDto : settingsRequestDto.getSettingsBiomeList()){
            if(settingsBiomeRequestDto.getBiomeId()==null || settingsBiomeRequestDto.getPercentage() == null || settingsBiomeRequestDto.getPercentage() <=0){
                continue;
            }
            double newValue = settingsBiomeRequestDto.getPercentage() / 100;
            totalPercentCovered += newValue;
            double threshold = values.get((int)(totalPixels * totalPercentCovered)-1);
            settingsBiomeRequestDto.setTreshold(threshold);
            settingsBiomeList.add(settingsBiomeRequestDto);

        }
        fillEmptyPixels(totalPercentCovered, settingsBiomeList);
        // On retrie la liste pour avoir les seuils dans l'ordre croissant
        settingsBiomeList.sort(Comparator.comparingDouble(SettingsBiomeRequestDto::getTreshold));



        // Créer la carte finale
        BiomeDto[][] map = new BiomeDto[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noiseMap[x][y];
                for (SettingsBiomeRequestDto settingsBiomeRequestDto : settingsBiomeList) {
                    if (value <= settingsBiomeRequestDto.getTreshold()) {
                        map[x][y] = biomeMap.get(settingsBiomeRequestDto.getBiomeId());
                        break;
                    }
                }
            }
        }

        return map;
    }
    
}
