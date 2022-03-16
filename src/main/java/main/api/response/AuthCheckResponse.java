package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import main.dto.UserDto;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AuthCheckResponse {

    private Boolean result;

    private UserDto user;

    public AuthCheckResponse() {
    }

    public AuthCheckResponse(boolean result) {
        this.result = result;
        if(!result){
            this.user = null;
        }
    }

    public Boolean isResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
