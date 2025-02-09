package com.castruche.laboratory_api.main_api.service.util;

import com.castruche.laboratory_api.main_api.dao.util.SettingRepository;
import com.castruche.laboratory_api.main_api.entity.util.Setting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    private static final Logger logger = LogManager.getLogger(SettingService.class);
    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public Setting getSettingByShortName(String shortName) {
        return settingRepository.findByShortName(shortName);
    }

    public String getSettingValueByShortName(String shortName) {
        Setting setting = settingRepository.findByShortName(shortName);
        if(setting != null) {
            return setting.getValue();
        }
        return null;
    }

    public String getMailjetApiKey() {
        return getSettingValueByShortName("mailjet_api_key");
    }

    public String getMailjetApiSecret() {
        return getSettingValueByShortName("mailjet_api_secret");
    }

    public String getMailjetApiSenderMail() {
        return getSettingValueByShortName("mailjet_api_sender_mail");
    }
    public String getStableDiffusionApiUrl() {
        return getSettingValueByShortName("stable_diffusion_api_url");
    }


}
