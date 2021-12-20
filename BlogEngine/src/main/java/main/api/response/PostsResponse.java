package main.api.response;

import main.dto.PostDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostsResponse {

    private Integer count;
    private List<PostDto> posts;

    public PostsResponse() {
    }

    public PostsResponse(Integer count, List<PostDto> posts) {
        this.count = count;
        this.posts = posts;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }
}
