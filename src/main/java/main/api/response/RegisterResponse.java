package main.api.response;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RegisterResponse {

    private boolean result;
    private Map<String,String> errors;

    public RegisterResponse() {
    }

    public RegisterResponse(Map<String, String> errors) {
        this.errors = errors;
        result = errors == null;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
