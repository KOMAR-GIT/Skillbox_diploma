package main.api.response;

import main.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class LoginResponse {

    private boolean result;

    private UserDTO user;

    public LoginResponse() {
    }

    public LoginResponse(boolean result, UserDTO user) {
        this.result = result;
        this.user = user;
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
