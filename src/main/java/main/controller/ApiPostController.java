package main.controller;

import main.api.request.AddCommentRequest;
import main.api.request.LikeDislikeRequest;
import main.api.request.ModerationDecisionRequest;
import main.api.response.*;
import main.dto.*;
import main.dto.interfaces.CommentInterface;
import main.dto.interfaces.PostInterface;
import main.model.enums.ModerationStatus;
import main.model.enums.PostOutputMode;
import main.model.enums.PostStatus;
import main.security.SecurityUser;
import main.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiPostController {

    private final PostsService postsService;
    private final PostCommentsService postCommentsService;
    private final TagsService tagsService;
    private final ModelMapper modelMapper;
    private final PostVotesService postVotesService;
    private final AuthCheckService authCheckService;

    public ApiPostController(PostsService postsService,
                             PostCommentsService postCommentsService,
                             TagsService tagsService,
                             ModelMapper modelMapper,
                             PostVotesService postVotesService, AuthCheckService authCheckService) {
        this.postsService = postsService;
        this.postCommentsService = postCommentsService;
        this.tagsService = tagsService;
        this.modelMapper = modelMapper;
        this.postVotesService = postVotesService;
        this.authCheckService = authCheckService;
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

    @GetMapping("/api/post/search")
    public ResponseEntity<PostsResponse> getPostsBySearch(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "query", defaultValue = "") String query
    ) {
        List<PostDto> posts = postsService.getPostsByQuery(offset, limit, query);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return ResponseEntity.ok(new PostsResponse(postsService.getPostsCount(query), posts));
    }

    @GetMapping("/api/post/byDate")
    public ResponseEntity<PostsResponse> getPostsByDate(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "date", defaultValue = "") String date
    ) {
        List<PostDto> posts = postsService.getPostsByDate(offset, limit, LocalDate.parse(date));
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return ResponseEntity.ok(new PostsResponse(postsService.getPostsCountByDate(date), posts));
    }

    @GetMapping("/api/post/byTag")
    public ResponseEntity<PostsResponse> getPostsByTag(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "tag", defaultValue = "") String tag
    ) {
        List<PostDto> posts = postsService.getPostsByTag(offset, limit, tag);
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return ResponseEntity.ok(new PostsResponse(postsService.getPostsCountByTag(tag), posts));
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostByIdResponse> getPostsById(
            @PathVariable Integer id) {
        PostInterface post = postsService.getPostById(id);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        boolean isIncreased = postsService.increasePostViewsCount(post);
        return ResponseEntity.ok(new PostByIdResponse(post,isIncreased, postCommentsService
                .getComments(id)
                .stream()
                .map(this::convertPostCommentToPostCommentsDTO)
                .collect(Collectors.toList()),
                tagsService.getPostTags(id)));
    }


    @GetMapping("/api/post/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostsResponse> getPostsForModeration(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status", defaultValue = "NEW") String status) {

        List<PostDto> posts =
                postsService.getPostsForModeration(offset, limit,
                        ModerationStatus.valueOf(status.toUpperCase()));
        posts.forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return ResponseEntity.ok(new PostsResponse(postsService.getPostsForModerationCount(), posts));
    }

    @PostMapping("/api/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<?> moderatePost(
            @RequestBody ModerationDecisionRequest request) {
        return ResponseEntity.ok(new SuccessResultResponse(postsService.getModerationDecision(request)));
    }

    @GetMapping("/api/post/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostsResponse> getUserPosts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status", defaultValue = "inactive") PostStatus status) {
        PostsResponse posts = postsService.getUserPosts(offset, limit, status);
        posts.getPosts().forEach(postDto -> postDto.editAnnounceText(postDto.getAnnounce()));
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/api/post")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseWithErrors> addPost(@RequestBody AddAndEditPostDto addAndEditPostDTO) {
        ResponseWithErrors responseWithErrors = postsService.addPost(addAndEditPostDTO);
        return ResponseEntity.ok(responseWithErrors);
    }

    @PostMapping(value = "/api/image")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> postImage(@RequestParam("image") MultipartFile photo) throws IOException {
        PostImageResponse response = authCheckService.postImage(photo);
        if (response.isResult()) {
            return ResponseEntity.ok(response.getPath());
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity<ResponseWithErrors> editPost(
            @PathVariable Integer id,
            @RequestBody AddAndEditPostDto addAndEditPostDTO) {
        ResponseWithErrors responseWithErrors = postsService.editPost(id, addAndEditPostDTO);
        return ResponseEntity.ok(responseWithErrors);
    }

    @PostMapping("/api/comment")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AddCommentResponse> addComment(@RequestBody AddCommentRequest addCommentRequest) {
        SecurityUser securityUser = (SecurityUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AddCommentResponse addCommentResponse = postCommentsService.addComment(
                addCommentRequest.getParentId(),
                addCommentRequest.getPostId(),
                addCommentRequest.getText(),
                securityUser.getUserId());

        if (addCommentResponse.getId() != null) {
            return ResponseEntity.ok(addCommentResponse);
        }
        return ResponseEntity.badRequest().body(addCommentResponse);

    }

    @PostMapping("/api/post/like")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<LikeDislikeResponse> like(@RequestBody LikeDislikeRequest likeDislikeRequest) {
        SecurityUser securityUser = (SecurityUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LikeDislikeResponse likeDislikeResponse = postVotesService.addLike(
                likeDislikeRequest.getPostId(),
                securityUser.getUserId());

        return ResponseEntity.ok(likeDislikeResponse);
    }

    @PostMapping("/api/post/dislike")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<LikeDislikeResponse> dislike(@RequestBody LikeDislikeRequest likeDislikeRequest) {
        SecurityUser securityUser = (SecurityUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LikeDislikeResponse likeDislikeResponse = postVotesService.addDislike(
                likeDislikeRequest.getPostId(),
                securityUser.getUserId());
        return ResponseEntity.ok(likeDislikeResponse);
    }


    public PostCommentsDto convertPostCommentToPostCommentsDTO(CommentInterface commentInterface) {
        PostCommentsDto postCommentsDTO = modelMapper.map(commentInterface, PostCommentsDto.class);
        postCommentsDTO.setTimestamp(postCommentsDTO.getTimestamp() / 1000);
        return postCommentsDTO;
    }

}
