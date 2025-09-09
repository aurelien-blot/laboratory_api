package com.castruche.laboratory_api.myworld_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConstantMyWorldUrl.TEST)
public class MWTestController {

    @GetMapping()
    public ResponseEntity<Boolean> connexionTest() {
        return ResponseEntity.ok(true);
    }


}
