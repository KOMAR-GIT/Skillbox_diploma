package main.service;

import main.dto.PostInterface;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PostsService {

    @Autowired
    private PostRepository postRepository;



    public PostsService() {

    }

    public Page<PostInterface> getPosts(int offset, int limit, PostOutputMode mode) {
        Page<PostInterface> posts;
        switch (mode){
            case early:
                posts = postRepository.getPostsOrderBy("p.time", "asc", PageRequest.of(offset, limit));
            case best:
            case popular:
                posts = postRepository.getPostsOrderBy("commentCount", "asc", PageRequest.of(offset, limit));
            case recent:
            default:
                posts = postRepository.getPostsOrderBy("p.time", "desc", PageRequest.of(offset, limit));
        }
        return posts;
    }

    public long getAllPostsCount(){
        return postRepository.count();
    }



}
