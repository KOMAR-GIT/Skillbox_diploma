package main.service;

import main.api.response.PostsResponse;
import main.api.response.ResponseWithErrors;
import main.dto.AddAndEditPostDTO;
import main.dto.ErrorsForAddingPost;
import main.dto.interfaces.PostInterface;
import main.model.Post;
import main.model.Tag;
import main.model.Tag2Post;
import main.model.enums.ModerationStatus;
import main.model.enums.PostStatus;
import main.repository.DAO.PostDAO;
import main.repository.DAO.builder.PostQueryBuilder;
import main.repository.PostRepository;
import main.security.SecurityUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
public class PostsService {

    private final PostRepository postRepository;
    private final PostDAO postDAO;
    private final UserService userService;
    private final Tag2PostService tag2PostService;
    private final TagsService tagsService;
    private final ModelMapper modelMapper;

    public PostsService(PostRepository postRepository,
                        PostDAO postDAO,
                        UserService userService,
                        Tag2PostService tag2PostService,
                        TagsService tagsService,
                        ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.postDAO = postDAO;
        this.userService = userService;
        this.tag2PostService = tag2PostService;
        this.tagsService = tagsService;
        this.modelMapper = modelMapper;
    }

    public List getPostsForModeration(int offset, int limit) {
        return postDAO.getPostsForModerationOrUserPosts(new PostQueryBuilder(offset, limit));
    }

    public List getPostsByMode(int offset, int limit, PostOutputMode mode) {
        String sortMode;
        switch (mode) {
            case early:
                sortMode = "p.time desc";
                break;
            case best:
                sortMode = "likeCount desc";
                break;
            case popular:
                sortMode = "commentCount desc";
                break;
            case recent:
            default:
                sortMode = "p.time";

        }
        return postDAO.getPosts(new PostQueryBuilder(offset, limit).order(sortMode));
    }

    public List getPostsByQuery(int offset, int limit, String searchQuery) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (!searchQuery.isEmpty()) {
            postQueryBuilder
                    .where("match(p.text, title) against(concat(\"*\",:searchQuery, \"*\") IN BOOLEAN MODE) ")
                    .parameter("searchQuery", searchQuery);
        }
        return postDAO.getPosts(postQueryBuilder);
    }

    public List getPostsByDate(int offset, int limit, LocalDate date) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (date != null) {
            postQueryBuilder
                    .where(" date(p.time) =  :date ")
                    .parameter("date", String.valueOf(date));
        }
        return postDAO.getPosts(postQueryBuilder);
    }

    public List getPostsByTag(int offset, int limit, String tag) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (!tag.isEmpty()) {
            postQueryBuilder
                    .where(" p.id in (select post_id from tag2post join tags t on tag_id = t.id where t.name = :tag) ")
                    .parameter("tag", tag);
        }
        return postDAO.getPosts(postQueryBuilder);
    }

    public PostsResponse getUserPosts(int offset, int limit, PostStatus status) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        String countFilterQuery;
        switch (status) {
            default:
            case inactive:
                countFilterQuery = " p.is_active = 0 ";
                postQueryBuilder.where(countFilterQuery);
                break;
            case pending:
                countFilterQuery = " p.is_active = 1 and p.moderation_status = 'NEW' ";
                postQueryBuilder.where(countFilterQuery);
                break;
            case declined:
                countFilterQuery = " p.is_active = 1 and p.moderation_status = 'DECLINED' ";
                postQueryBuilder.where(countFilterQuery);
                break;
            case published:
                countFilterQuery = " p.is_active = 1 and p.moderation_status = 'ACCEPTED' ";
                postQueryBuilder.where(countFilterQuery);
                break;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        postQueryBuilder.where(" u.id = " + securityUser.getUserId());
        return new PostsResponse(postRepository.getUserPostsCount(countFilterQuery),
                postDAO.getPostsForModerationOrUserPosts(postQueryBuilder));
    }


    public PostInterface getPostById(int id) {
        PostInterface post = postRepository.getPostById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = auth.getName().equals("anonymousUser")
                ? null
                : (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (securityUser == null
                || !securityUser.getUserId().equals(post.getUserId())
                || securityUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("user:moderate"))) {
            if (post.getActive() == 1 && post.getModerationStatus() == ModerationStatus.ACCEPTED
                    && post.getTimestamp().getTime() <= Calendar.getInstance().getTime().getTime()) {
                return post;
            } else {
                return null;
            }
        } else {
            return post;
        }
    }

    public void increasePostViewsCount(PostInterface post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals("anonymousUser")) {
            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
            if (!securityUser.getUserId().equals(post.getUserId())
                    && securityUser.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("user:moderate"))) {
                postRepository.increasePostViewsCount(post.getId());
            }
        }
    }

    public ResponseWithErrors addPost(AddAndEditPostDTO addAndEditPostDTO) {
        HashMap<String, String> errors = new HashMap<>();
        checkTextAndString(errors, addAndEditPostDTO.getTitle(), addAndEditPostDTO.getText());

        if (errors.size() == 0) {

            Post post = new Post();

            post.setActive(addAndEditPostDTO.isActive());
            post.setModerationStatus(ModerationStatus.NEW);
            post.setTitle(addAndEditPostDTO.getTitle());
            post.setText(addAndEditPostDTO.getText());
            post.setViewCount(0);

            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            post.setUser(userService.getUserById(securityUser.getUserId()));

            if (addAndEditPostDTO.getTimestamp() < System.currentTimeMillis() / 1000) {
                post.setTime(Calendar.getInstance().getTime());
            } else {
                post.setTime(new Date(addAndEditPostDTO.getTimestamp() * 1000));
            }

            postRepository.save(post);
            tag2PostService.putRelations(addAndEditPostDTO.getTags(), post);

            return new ResponseWithErrors(true, null);
        }

        return new ResponseWithErrors(false, errors);
    }

    public ResponseWithErrors editPost(int id, AddAndEditPostDTO addAndEditPostDTO) {
        HashMap<String, String> errors = new HashMap<>();
        checkTextAndString(errors, addAndEditPostDTO.getTitle(), addAndEditPostDTO.getText());

        if (errors.size() == 0) {
            PostInterface postInterface = postRepository.getPostById(id);
            Post post = modelMapper.map(postInterface, Post.class);
            post.setActive(addAndEditPostDTO.isActive());
            post.setModerationStatus(ModerationStatus.NEW);
            post.setTitle(addAndEditPostDTO.getTitle());
            post.setText(addAndEditPostDTO.getText());
            post.setViewCount(0);

            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            post.setUser(userService.getUserById(securityUser.getUserId()));

            if (addAndEditPostDTO.getTimestamp() < System.currentTimeMillis() / 1000) {
                post.setTime(Calendar.getInstance().getTime());
            } else {
                post.setTime(new Date(addAndEditPostDTO.getTimestamp() * 1000));
            }

            postRepository.save(post);

            List<Integer> currentTagId = tag2PostService
                    .getRelationsByPostId(post.getId())
                    .stream()
                    .map(t -> t.getTagId().getId())
                    .collect(Collectors.toList());

            List<Tag> newTags = tagsService.getTagsByNames(addAndEditPostDTO.getTags());
            List<Integer> newTagsId = newTags
                    .stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());

            if (!(currentTagId.containsAll(newTagsId) && newTagsId.containsAll(currentTagId))) {
                List<Tag2Post> relations = newTags.stream().map(t -> new Tag2Post(post, t)).collect(Collectors.toList());
                tag2PostService.overwritePostRelations(post.getId(), relations);
            } else if (currentTagId.size() == 0) {
                tag2PostService.putRelations(addAndEditPostDTO.getTags(), post);
            }
            return new ResponseWithErrors(true, null);
        }
        return new ResponseWithErrors(false, errors);
    }

    private void checkTextAndString(HashMap<String, String> map, String title, String text) {
        if (title.length() < 5) map.put("title", ErrorsForAddingPost.title);
        if (text.length() < 50) map.put("text", ErrorsForAddingPost.text);
    }


    //Различные методы по получению количества постов

    public Integer getPostsCount() {
        return postRepository.getPostsCount();
    }

    public Integer getPostsForModerationCount() {
        return postRepository.getPostsForModerationCount();
    }

    public Integer getUserPostsCount() {
        return postRepository.getPostsForModerationCount();
    }

    public Integer getPostsCount(String searchQuery) {
        return postRepository.getQueriedPostsCount(searchQuery);
    }

    public Integer getPostsCountByDate(String date) {
        return postRepository.getPostsCountByDate(date);
    }

    public Integer getPostsCountByTag(String tag) {
        return postRepository.getPostsCountByTag(tag);
    }


}