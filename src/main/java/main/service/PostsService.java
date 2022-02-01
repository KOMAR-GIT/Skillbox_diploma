package main.service;

import main.api.response.PostsResponse;
import main.dto.interfaces.PostInterface;
import main.model.enums.PostStatus;
import main.model.enums.Role;
import main.repository.DAO.PostDAO;
import main.repository.DAO.builder.PostQueryBuilder;
import main.repository.PostRepository;
import main.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@Service
public class PostsService {

    private final PostRepository postRepository;
    private final PostDAO postDAO;

    public PostsService(PostRepository postRepository, PostDAO postDAO) {
        this.postRepository = postRepository;
        this.postDAO = postDAO;
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

    public PostsResponse getUserPosts(int offset, int limit, PostStatus status, Principal principal) {
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
        postQueryBuilder.where(" u.email = '" + principal.getName() + "'");
        return new PostsResponse(postRepository.getUserPostsCount(countFilterQuery),
                postDAO.getPostsForModerationOrUserPosts(postQueryBuilder));
    }


    public PostInterface getPostById(int id) {
        return postRepository.getPostById(id);
    }

    public void increasePostViewsCount(PostInterface post, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());

        if (!auth.getName().equals("anonymousUser")) {
            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
            List s = (List) securityUser.getAuthorities();
            if (!securityUser.getUserId().equals(post.getUserId())
                    && !securityUser.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("user:moderate"))) {
                postRepository.increasePostViewsCount(post.getId());
            }
        }
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
