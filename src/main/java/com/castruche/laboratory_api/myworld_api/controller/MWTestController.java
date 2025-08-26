package com.castruche.laboratory_api.myworld_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConstantMyWorldUrl.TEST)
public class MWTestController {

    @GetMapping()
    public boolean connexionTest() {
        return true;
    }


}
