package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class PostImageResponse {


    private Boolean result;
    private Map<String, String> errors;
    private String path;

    public PostImageResponse() {
    }

    public PostImageResponse(boolean result, Map<String, String> errors, String path) {
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
