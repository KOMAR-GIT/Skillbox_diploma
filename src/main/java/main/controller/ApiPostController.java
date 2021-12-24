package main.controller;

import main.api.response.PostsResponse;
import main.dto.PostDto;
import main.service.PostOutputMode;
import main.service.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiPostController {


    private final  PostsService postsService;


    private final  ModelMapper modelMapper;

    public ApiPostController(PostsService postsService, ModelMapper modelMapper) {
        this.postsService = postsService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/api/post")
    private ResponseEntity<PostsResponse> posts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "mode", defaultValue = "recent") PostOutputMode mode
    ) {
        List<PostDto> posts = postsService.getPosts(offset, limit, mode);

        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));

        PostsResponse postsResponse = new PostsResponse(postsService.getAllPostsCount(), posts);
        return new ResponseEntity<>(postsResponse, HttpStatus.OK);
    }


}
