package com.castruche.laboratory_api.myworld_api.controller;

import com.castruche.laboratory_api.myworld_api.dto.post.PostDto;
import com.castruche.laboratory_api.myworld_api.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ConstantMyWorldUrl.POST)
public class MWPostController {


    private PostService postService;

    public MWPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public ResponseEntity<List<PostDto>> getAll() {
        return ResponseEntity.ok(postService.getAllDto());
    }

    @PostMapping()
    public ResponseEntity<PostDto> createPost(
            @RequestPart("post") PostDto postDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        PostDto saved = postService.createWithFiles(postDto, files);

        return ResponseEntity
                .status(201) // Created
                .body(saved);
    }


}
