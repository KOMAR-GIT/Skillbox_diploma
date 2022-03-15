package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import main.dto.UserDTO;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AuthCheckResponse {

    private Boolean result;

    private UserDTO user;

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
