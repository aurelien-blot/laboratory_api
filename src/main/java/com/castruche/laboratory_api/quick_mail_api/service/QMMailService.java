package com.castruche.laboratory_api.quick_mail_api.service;

import com.castruche.laboratory_api.main_api.service.util.SettingService;
import com.castruche.laboratory_api.quick_mail_api.dto.MailDto;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class QMMailService {

    private static final Logger logger = LogManager.getLogger(QMMailService.class);
    private static final String QUICK_MAIL_TEMPLATE_SHORTNAME = "mailjet_quick_mail_id";

    private final SettingService settingService;
    public QMMailService(SettingService settingService) {
        this.settingService = settingService;
    }

    public void sendMail(MailDto mailDto) {
        JSONObject variables = new JSONObject();
        variables.put("body", mailDto.getBody().replace("\n", "<br>"));
        variables.put("subject", mailDto.getSubject());
        variables.put("senderName", mailDto.getSenderName());
        variables.put("senderMail", mailDto.getSenderMail());

        String templateId = settingService.getSettingValueByShortName(QUICK_MAIL_TEMPLATE_SHORTNAME);

        try {
            String apiKey = settingService.getMailjetApiKey();
            String apiSecret = settingService.getMailjetApiSecret();
            String senderMail = settingService.getMailjetApiSenderMail();
            if(null==apiKey || apiKey.isEmpty() || null==apiSecret || apiSecret.isEmpty()){
                throw new MailjetException("Mailjet API key or secret is not set");
            }
            if(null==senderMail || senderMail.isEmpty()){
                throw new MailjetException("Mailjet API sender mail is not configured");
            }
           if(null==templateId || templateId.isEmpty()){
                throw new MailjetException("Mailjet API template is not configured");
            }
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            client = new MailjetClient(
                    apiKey,
                    apiSecret,
                    new ClientOptions("v3.1"));
            request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", senderMail)
                                            .put("Name", "QuickMail"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", mailDto.getTo())
                                                    .put("Name", mailDto.getTo())))
                                    .put(Emailv31.Message.TEMPLATEID, Integer.parseInt(templateId))
                                    .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                    .put(Emailv31.Message.VARIABLES, variables)
                            ));
            response= client.post(request);
            if(response.getStatus() != 200){
                throw new MailjetException(response.getData().toString());
            }
            logger.info("QuickMail envoyé à : " + mailDto.getTo());
        }
        catch (MailjetException | MailjetSocketTimeoutException e) {
            logger.error("Mailjet error: " + e.getMessage());
            throw new RuntimeException("Mailjet error: " + e.getMessage());
        }

    }
    
}
