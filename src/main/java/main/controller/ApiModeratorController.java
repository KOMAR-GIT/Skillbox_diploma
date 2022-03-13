package main.controller;

import main.api.response.PostsResponse;
import main.dto.PostDto;
import main.service.PostOutputMode;
import main.service.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiModeratorController {

    private final PostsService postsService;



    public ApiModeratorController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/api/post")
    public ResponseEntity<PostsResponse> posts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "mode", defaultValue = "recent") PostOutputMode mode
    ) {
        List<PostDto> posts = postsService.getPostsByMode(offset, limit, mode);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return ResponseEntity.ok(new PostsResponse(postsService.getPostsCount(), posts));
    }
    
}
