package main.api.response;

import org.springframework.stereotype.Component;

@Component
public class LogoutResponse {

    private boolean result;

    public LogoutResponse() {
    }

    public LogoutResponse(boolean result) {
        this.result = result;
    }


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
