package main.api.response;

import main.dto.UserDTO;
import main.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthCheckResponse {

    private boolean result;

    private UserDTO user;

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
