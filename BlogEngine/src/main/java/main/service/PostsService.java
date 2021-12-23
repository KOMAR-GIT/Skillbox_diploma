package main.service;

import main.dto.PostDto;
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

    public List<PostDto> getPosts(int offset, int limit, PostOutputMode mode) {
        return postDAO.getPostsBySort(offset, limit, mode);
    }

    public Integer getAllPostsCount() {
        return postRepository.getPostsCount();
    }
}
