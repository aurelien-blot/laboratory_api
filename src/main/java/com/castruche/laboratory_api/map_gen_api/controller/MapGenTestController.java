package com.castruche.laboratory_api.map_gen_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConstantUrlMap.TEST)
public class MapGenTestController {


    @GetMapping()
    public String connexionTestMapGen() {
        return "Test Map Gen OK";
    }


}
