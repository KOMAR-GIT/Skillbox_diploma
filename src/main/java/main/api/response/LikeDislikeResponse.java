package main.api.response;

import org.springframework.stereotype.Component;

@Component
public class LikeDislikeResponse {

    private boolean result;

    public LikeDislikeResponse() {
    }

    public LikeDislikeResponse(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
