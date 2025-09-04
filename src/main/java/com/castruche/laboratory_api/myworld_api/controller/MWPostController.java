package com.castruche.laboratory_api.myworld_api.controller;

import com.castruche.laboratory_api.myworld_api.dto.post.PostDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ConstantMyWorldUrl.POST)
public class MWPostController {

    @GetMapping()
    public List<PostDto> getAll() {
        List<PostDto> results = new ArrayList<>();
        PostDto post1 = new PostDto();
        post1.setId(1L);
        post1.setContent("This is the first post ");
        results.add(post1);
        PostDto post2 = new PostDto();
        post2.setId(2L);
        post2.setContent("This is the second post ");
        results.add(post2);

        return results;
    }


}
