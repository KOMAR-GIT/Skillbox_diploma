package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import main.dto.UserDto;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class LoginResponse {

    private boolean result;

    private UserDto user;

    public LoginResponse() {
    }

    public LoginResponse(boolean result, UserDto user) {
        this.result = result;
        this.user = user;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
