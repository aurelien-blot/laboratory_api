package com.castruche.laboratory_api.myworld_api.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping(ConstantMyWorldUrl.FILE)
public class MWFileController {


    @GetMapping()
    public ResponseEntity<Resource> getPicture(@RequestParam String filePath) {
        // TODO: check if the file is accessible by the user
        if(null == filePath) return ResponseEntity.notFound().build();

        Path p = Path.of(filePath);
        if (!Files.exists(p)) return ResponseEntity.notFound().build();

        try{
            Resource res = new UrlResource(p.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .cacheControl(CacheControl.noCache()) // ou max-age court si tu veux
                    .body(res);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }



}
