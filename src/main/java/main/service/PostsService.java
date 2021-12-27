package main.service;

import main.repository.DAO.PostDAO;
import main.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Service
public class PostsService {

    private final PostRepository postRepository;
    private final PostDAO postDAO;

    public PostsService(PostRepository postRepository, PostDAO postDAO) {
        this.postRepository = postRepository;
        this.postDAO = postDAO;
    }

    public List getPosts(int offset, int limit, PostOutputMode mode, String searchQuery) {
        return postDAO.getPostsBySortAndSearch(offset, limit, mode, searchQuery);
    }

    public Integer getAllPostsCount() {
        return postRepository.getPostsCount();
    }

    public Integer getQueriedPostsCount(String searchQuery){
        return postRepository.getQueriedPostsCount(searchQuery);
    }
}
