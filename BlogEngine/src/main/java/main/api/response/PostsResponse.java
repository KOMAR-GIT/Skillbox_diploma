package main.api.response;

import main.dto.PostDto;
import main.model.Post;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostsResponse {

    private int count;
    private List<PostDto> posts;

    public PostsResponse() {
    }

    public PostsResponse(int count, List<PostDto> posts) {
        this.count = count;
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }
}
