package com.castruche.laboratory_api.myworld_api.service.util;

import com.castruche.laboratory_api.main_api.dao.util.SettingRepository;
import com.castruche.laboratory_api.main_api.entity.util.Setting;
import com.castruche.laboratory_api.main_api.service.util.SettingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MWSettingService extends SettingService{

    private static final Logger logger = LogManager.getLogger(MWSettingService.class);
    private SettingRepository settingRepository;

    public MWSettingService(SettingRepository settingRepository) {
        super(settingRepository);
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


    public String getFrontBaseUrl() {
        return getSettingValueByShortName("front_base_url");
    }
    public List<Integer> getMailVerificationDelayList() {
        try {
            String setting = getSettingValueByShortName("mail_verification_reminder_delay_list");
            List<Integer> result = null;
            if (setting != null) {
                result = new ArrayList<>();
                String[] split = setting.split(";");
                for (String s : split) {
                    result.add(Integer.parseInt(s));
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("Erreur de récupération du paramètre des délais de relance des vérifications de mail", e);
        }
        return null;
    }

    public Integer getDeletingUnverifiedAccountDelay() {
        String result = getSettingValueByShortName("deleting_unverified_account_delay");
        try {
            return Integer.parseInt(result);
        } catch (NumberFormatException e) {
            logger.error("Error while parsing deleting_unverified_account_delay setting", e);
        }
        return null;
    }

    public String getMailVerificationId() {
        return getSettingValueByShortName("mailjet_verify_mail_id");
    }
    public String getMailVerificationReminderId() {
        return getSettingValueByShortName("mailjet_account_remind_verification_id");
    }
    public String getMailRegistrationId() {
        return getSettingValueByShortName("mailjet_registration_id");
    }
    public String getMailAccountDeletionId() {
        return getSettingValueByShortName("mailjet_account_deletion_id");
    }
    public String getMailRequestContactId() {
        return getSettingValueByShortName("mailjet_request_contact_id");
    }
    public String getMailRequestContactByMailId() {
        return getSettingValueByShortName("mailjet_request_contact_by_mail_id");
    }
    public String getMailResetPasswordId() {
        return getSettingValueByShortName("mailjet_reset_password_id");
    }

}
