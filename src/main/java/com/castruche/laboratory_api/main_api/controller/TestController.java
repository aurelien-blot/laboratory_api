package com.castruche.laboratory_api.main_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConstantUrl.TEST)
public class TestController {

    @GetMapping()
    public boolean connexionTest() {
        return true;
    }


}
