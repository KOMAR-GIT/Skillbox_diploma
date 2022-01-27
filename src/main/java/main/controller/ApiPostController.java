package main.controller;

import main.api.response.PostByIdResponse;
import main.api.response.PostsResponse;
import main.dto.*;
import main.dto.interfaces.CommentInterface;
import main.dto.interfaces.PostInterface;
import main.model.Post;
import main.model.enums.PostStatus;
import main.service.PostCommentsService;
import main.service.PostOutputMode;
import main.service.PostsService;
import main.service.TagsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiPostController {


    private final PostsService postsService;
    private final PostCommentsService postCommentsService;
    private final TagsService tagsService;

    private final ModelMapper modelMapper;


    public ApiPostController(PostsService postsService, PostCommentsService postCommentsService, TagsService tagsService, ModelMapper modelMapper) {
        this.postsService = postsService;
        this.postCommentsService = postCommentsService;
        this.tagsService = tagsService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/api/post")
    public ResponseEntity<PostsResponse> posts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "mode", defaultValue = "recent") PostOutputMode mode
    ) {
        List<PostDto> posts = postsService.getPostsByMode(offset, limit, mode);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return new ResponseEntity<>(new PostsResponse(postsService.getPostsCount(), posts), HttpStatus.OK);
    }

    @GetMapping("/api/post/search")
    public ResponseEntity<PostsResponse> getPostsBySearch(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "query", defaultValue = "") String query
    ) {
        List<PostDto> posts = postsService.getPostsByQuery(offset, limit, query);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return new ResponseEntity<>(new PostsResponse(postsService.getPostsCount(query), posts), HttpStatus.OK);
    }

    @GetMapping("/api/post/byDate")
    public ResponseEntity<PostsResponse> getPostsByDate(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "date", defaultValue = "") String date
    ) {
        List<PostDto> posts = postsService.getPostsByDate(offset, limit, LocalDate.parse(date));
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return new ResponseEntity<>(new PostsResponse(postsService.getPostsCountByDate(date), posts), HttpStatus.OK);
    }

    @GetMapping("/api/post/byTag")
    public ResponseEntity<PostsResponse> getPostsByTag(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "tag", defaultValue = "") String tag
    ) {
        List<PostDto> posts = postsService.getPostsByTag(offset, limit, tag);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return new ResponseEntity<>(new PostsResponse(postsService.getPostsCountByTag(tag), posts), HttpStatus.OK);
    }

    //Не реализовано увеличение количества просмотров
    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostByIdResponse> getPostsById(@PathVariable Integer id) {
        PostInterface post = postsService.getPostById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(new PostByIdResponse(post, postCommentsService
                .getComments(id)
                .stream()
                .map(this::convertPostCommentToPostCommentsDTO)
                .collect(Collectors.toList()),
                tagsService.getPostTags(id)), HttpStatus.OK);
    }

    @GetMapping("/api/post/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostsResponse> getPostsForModeration(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<PostDto> posts = postsService.getPostsForModeration(offset, limit);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return new ResponseEntity<>(new PostsResponse(postsService.getPostsForModerationCount(), posts), HttpStatus.OK);
    }

    @GetMapping("/api/post/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostsResponse> getUserPosts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status", defaultValue = "inactive") PostStatus status,
            Principal principal) {
        List<PostDto> posts = postsService.getUserPosts(offset, limit, status, principal);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return new ResponseEntity<>(new PostsResponse(postsService.getPostsForModerationCount(), posts), HttpStatus.OK);
    }


    public PostCommentsDTO convertPostCommentToPostCommentsDTO(CommentInterface commentInterface) {
        PostCommentsDTO postCommentsDTO = modelMapper.map(commentInterface, PostCommentsDTO.class);
        postCommentsDTO.setTimestamp(postCommentsDTO.getTimestamp() / 1000);
        return postCommentsDTO;
    }

}
