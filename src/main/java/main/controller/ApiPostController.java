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

import java.time.LocalDate;
import java.util.List;

@RestController
public class ApiPostController {


    private final PostsService postsService;

    private final ModelMapper modelMapper;

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
        return new ResponseEntity<>(new PostsResponse(postsService.getAllPostsCount(), posts), HttpStatus.OK);
    }

    @GetMapping("/api/post/search")
    private ResponseEntity<PostsResponse> getPostsBySearch(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "query", defaultValue = "") String query
    ){
        List<PostDto> posts = postsService.getPosts(offset, limit, query);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return new ResponseEntity<>(new PostsResponse(postsService.getQueriedPostsCount(query), posts), HttpStatus.OK);
    }

    @GetMapping("/api/post/byDate")
    private ResponseEntity<PostsResponse> getPostsByDate(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "date", defaultValue = "") String date
            ) {
        List<PostDto> posts = postsService.getPosts(offset, limit, LocalDate.parse(date));
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return new ResponseEntity<>(new PostsResponse(postsService.getAllPostsCount(), posts), HttpStatus.OK);
    }

}
