package main.api.response;

import main.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthCheckResponse {

    private boolean result;

    private User user;

    public AuthCheckResponse() {
    }

    public AuthCheckResponse(boolean result) {
        this.result = result;
        if(!result){
            this.user = null;
        }
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
