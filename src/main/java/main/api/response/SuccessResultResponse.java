package main.api.response;

import org.springframework.stereotype.Component;

@Component
public class SuccessResultResponse {

    private boolean result;

    public SuccessResultResponse() {
    }

    public SuccessResultResponse(boolean result) {
        this.result = result;
    }


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
