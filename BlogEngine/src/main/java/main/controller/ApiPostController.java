package main.controller;

import main.api.response.PostsResponse;
import main.dto.PostDto;
import main.dto.PostInterface;
import main.dto.UserDtoForPost;
import main.model.*;
import main.repository.PostCommentRepository;
import main.repository.PostVotesRepository;
import main.repository.UserRepository;
import main.service.PostOutputMode;
import main.service.PostsService;
import org.aspectj.weaver.BoundedReferenceType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class ApiPostController {

    @Autowired
    PostsService postsService;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping("/api/post")
    private ResponseEntity<PostsResponse> posts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "mode", defaultValue = "recent") PostOutputMode mode
    ) {
        Page<PostInterface> posts = postsService.getPosts(offset, limit, mode);

        PostsResponse postsResponse = new PostsResponse((int) posts.getTotalElements(),
                posts.stream().map(this::convertPostToDto).collect(Collectors.toList()));

        return new ResponseEntity<>(postsResponse, HttpStatus.OK);
    }

    private PostDto convertPostToDto(PostInterface post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.convertTimeToTimestamp(post.getTimestamp());
        postDto.setUser(new UserDtoForPost(post.getUserId(), post.getName()));


        return postDto;
    }


}
