package main.api.response;

import main.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostsResponse {

    private int count;
    private Post[] posts;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }
}
