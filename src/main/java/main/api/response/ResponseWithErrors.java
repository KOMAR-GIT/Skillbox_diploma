package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class ResponseWithErrors {

    private boolean result;
    private Map<String,String> errors;

    public ResponseWithErrors() {
    }

    public ResponseWithErrors(boolean result ,Map<String, String> errors) {
        this.errors = errors;
        this.result = result;
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
