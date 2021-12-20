package main.service;

import main.dto.PostInterface;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
                posts = postRepository.getPostsOrderBy("p.time desc", PageRequest.of(offset, limit));
                break;
            case best:
            case popular:
                posts = postRepository.getPostsOrderBy("commentCount", PageRequest.of(offset, limit));
                break;
            case recent:
            default:
                posts = postRepository.getPostsOrderBy("posts.time asc", PageRequest.of(offset, limit));
                break;
        }

        System.out.println(posts.getContent().get(0).getId());
        return posts;
    }

    public long getAllPostsCount(){
        return postRepository.count();
    }



}
