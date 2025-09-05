package com.castruche.laboratory_api.myworld_api.controller;

import com.castruche.laboratory_api.myworld_api.dto.post.PostDto;
import com.castruche.laboratory_api.myworld_api.service.PostService;
import org.springframework.web.bind.annotation.*;

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
    public List<PostDto> getAll() {
        return postService.getAllDto();
    }

    @PostMapping()
    public PostDto create(@RequestBody PostDto postDto) {
        return postService.create(postDto);
    }


}
