package com.castruche.laboratory_api.myworld_api.controller;

import com.castruche.laboratory_api.myworld_api.service.PictureService;
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


    private final PictureService pictureService;

    public MWFileController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/miniature/{pictureId}")
    public ResponseEntity<Resource> downloadMiniaturePicture(@PathVariable Long pictureId) {
        return pictureService.downloadMiniaturePicture(pictureId);
    }

    @GetMapping("/resized/{pictureId}")
    public ResponseEntity<Resource> downloadResizedPicture(@PathVariable Long pictureId) {
        return pictureService.downloadResizedPicture(pictureId);
    }

    @GetMapping("/original/{pictureId}")
    public ResponseEntity<Resource> downloadOriginal(@PathVariable Long pictureId) {
        return pictureService.downloadOriginalPicture(pictureId);
    }

    @GetMapping("/check-picture-status")
    public ResponseEntity<Boolean> checkPictureStatus() {
        return pictureService.checkPictureStatus();
    }


}
