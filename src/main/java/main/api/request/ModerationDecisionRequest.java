package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModerationDecisionRequest {

    @JsonProperty("post_id")
    private int postId;
    private String decision;

    public ModerationDecisionRequest() {
    }

    public ModerationDecisionRequest(int postId, String decision) {
        this.postId = postId;
        this.decision = decision;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
