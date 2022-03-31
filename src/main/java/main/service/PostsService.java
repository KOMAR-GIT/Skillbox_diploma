package main.service;

import main.api.request.ModerationDecisionRequest;
import main.api.response.PostsResponse;
import main.api.response.ResponseWithErrors;
import main.dto.AddAndEditPostDto;
import main.dto.errorMessages.ErrorsForAddingPost;
import main.dto.interfaces.PostInterface;
import main.model.Post;
import main.model.Tag;
import main.model.Tag2Post;
import main.model.User;
import main.model.enums.ModerationStatus;
import main.model.enums.PostOutputMode;
import main.model.enums.PostStatus;
import main.repository.DAO.builder.PostQueryBuilder;
import main.repository.PostRepository;
import main.security.SecurityUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostsService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final Tag2PostService tag2PostService;
    private final TagsService tagsService;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public PostsService(PostRepository postRepository,
                        UserService userService,
                        Tag2PostService tag2PostService,
                        TagsService tagsService,
                        ModelMapper modelMapper,
                        EntityManager entityManager) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.tag2PostService = tag2PostService;
        this.tagsService = tagsService;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    public List getPostsForModeration(int offset, int limit, ModerationStatus status) {
        return postRepository.getPostsForModerator(
                new PostQueryBuilder(offset, limit).where(" p.moderation_status = \"" + status + "\""),
                entityManager);
    }

    public boolean getModerationDecision(ModerationDecisionRequest request) {
        Post post = postRepository.findById(request.getPostId()).orElse(null);
        if (post != null) {

            switch (request.getDecision()) {
                case "accept":
                    post.setModerationStatus(ModerationStatus.ACCEPTED);
                    break;
                case "decline":
                    post.setModerationStatus(ModerationStatus.DECLINED);
                    break;
                default:
                    return false;
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
            User user = new User();
            user.setId(securityUser.getUserId());
            post.setModerator(user);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    public Post findPostById(int id) {
        return postRepository.findById(id).orElse(null);
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
        return postRepository.getPosts(new PostQueryBuilder(offset, limit).order(sortMode), entityManager);
    }

    public List getPostsByQuery(int offset, int limit, String searchQuery) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (!searchQuery.isEmpty()) {
            postQueryBuilder
                    .where("match(p.text, title) against(concat(\"*\",:searchQuery, \"*\") IN BOOLEAN MODE) ")
                    .parameter("searchQuery", searchQuery);
        }
        return postRepository.getPosts(postQueryBuilder, entityManager);
    }

    public List getPostsByDate(int offset, int limit, LocalDate date) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (date != null) {
            postQueryBuilder
                    .where(" date(p.time) =  :date ")
                    .parameter("date", String.valueOf(date));
        }
        return postRepository.getPosts(postQueryBuilder, entityManager);
    }

    public List getPostsByTag(int offset, int limit, String tag) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (!tag.isEmpty()) {
            postQueryBuilder
                    .where(" p.id in " +
                            "   (select " +
                            "       post_id " +
                            "    from " +
                            "       tag2post " +
                            "    join tags t on tag_id = t.id " +
                            "    where t.name = :tag) ")
                    .parameter("tag", tag);
        }
        return postRepository.getPosts(postQueryBuilder, entityManager);
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
                postRepository.getPostsForUser(postQueryBuilder, entityManager));
    }


    public PostInterface getPostById(int id) {
        PostInterface post = postRepository.getPostById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAnonymous = auth.getName().equals("anonymousUser");

        SecurityUser securityUser = isAnonymous ? null
                : (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isModerator = !isAnonymous && securityUser
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("user:moderate"));


        if (securityUser == null
                || !securityUser.getUserId().equals(post.getUserId())
                || isModerator) {
            if (post.getActive() == 1 && post.getModerationStatus() == ModerationStatus.ACCEPTED
                    && post.getTimestamp().getTime() <= Calendar.getInstance().getTime().getTime()) {
                return post;
            } else if (post.getActive() == 1 && isModerator) {
                return post;
            } else {
                return null;
            }
        } else {
            return post;
        }
    }

    public boolean increasePostViewsCount(PostInterface post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals("anonymousUser")) {
            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
            if (!securityUser.getUserId().equals(post.getUserId())
                    && securityUser
                    .getAuthorities()
                    .stream()
                    .noneMatch(a -> a.getAuthority().equals("user:moderate"))) {
                postRepository.increasePostViewsCount(post.getId());
                return true;
            }
            return false;
        } else {
            postRepository.increasePostViewsCount(post.getId());
            return true;
        }
    }

    public ResponseWithErrors addPost(AddAndEditPostDto addAndEditPostDTO) {
        HashMap<String, String> errors = new HashMap<>();
        checkTextAndString(errors, addAndEditPostDTO.getTitle(), addAndEditPostDTO.getText());

        if (errors.size() == 0) {

            Post post = new Post();

            post.setActive(addAndEditPostDTO.isActive());
            post.setModerationStatus(ModerationStatus.NEW);
            post.setTitle(addAndEditPostDTO.getTitle());
            post.setText(addAndEditPostDTO.getText());
            post.setViewCount(0);

            SecurityUser securityUser = (SecurityUser)
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal();
            post.setUser(userService.getUserById(securityUser.getUserId()));

            if (addAndEditPostDTO.getTimestamp() < System.currentTimeMillis() / 1000) {
                post.setTime(Calendar.getInstance().getTime());
            } else {
                post.setTime(new Date(addAndEditPostDTO.getTimestamp() * 1000));
            }
            postRepository.save(post);
            tagsService.addTags(addAndEditPostDTO.getTags());
            tag2PostService.putRelations(addAndEditPostDTO.getTags(), post);

            return new ResponseWithErrors(true, null);
        }

        return new ResponseWithErrors(false, errors);
    }

    public ResponseWithErrors editPost(int id, AddAndEditPostDto addAndEditPostDTO) {
        HashMap<String, String> errors = new HashMap<>();
        checkTextAndString(errors, addAndEditPostDTO.getTitle(), addAndEditPostDTO.getText());

        if (errors.size() == 0) {
            PostInterface postInterface = postRepository.getPostById(id);
            Post post = modelMapper.map(postInterface, Post.class);

            SecurityUser securityUser = (SecurityUser)
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal();
            post.setUser(userService.getUserById(securityUser.getUserId()));

            boolean isModerator = securityUser
                    .getAuthorities()
                    .stream()
                    .anyMatch(a -> a.getAuthority().equals("user:moderate"));

            post.setActive(isModerator ? post.isActive() : addAndEditPostDTO.isActive());
            post.setTitle(addAndEditPostDTO.getTitle());
            post.setText(addAndEditPostDTO.getText());


            post.setModerationStatus(isModerator ? post.getModerationStatus() : ModerationStatus.NEW);

            if (addAndEditPostDTO.getTimestamp() < System.currentTimeMillis() / 1000) {
                post.setTime(Calendar.getInstance().getTime());
            } else {
                post.setTime(new Date(addAndEditPostDTO.getTimestamp() * 1000));
            }

            postRepository.save(post);

            tagsService.addTags(addAndEditPostDTO.getTags());

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
                List<Tag2Post> relations = newTags
                        .stream()
                        .map(t -> new Tag2Post(post, t))
                        .collect(Collectors.toList());
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