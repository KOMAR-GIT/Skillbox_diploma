package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeDislikeRequest {

    @JsonProperty("post_id")
    private Integer postId;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
