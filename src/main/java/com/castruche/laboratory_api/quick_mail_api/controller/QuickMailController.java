package com.castruche.laboratory_api.quick_mail_api.controller;

import com.castruche.laboratory_api.quick_mail_api.dto.MailDto;
import com.castruche.laboratory_api.quick_mail_api.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantUrlQuickMail.ROOT)
public class QuickMailController {


    private final MailService mailService;

    public QuickMailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping()
    public ResponseEntity<String> sendMail(@RequestBody MailDto mailDto) {
        try{
            this.mailService.sendMail(mailDto);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            //On envoie le message d'erreur
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
