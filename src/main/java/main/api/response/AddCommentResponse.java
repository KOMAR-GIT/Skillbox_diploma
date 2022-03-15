package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AddCommentResponse {

    private Integer id;
    private Boolean result;
    private Map<String, String> errors;

    public AddCommentResponse() {
    }

    public AddCommentResponse(int id) {
        this.id = id;
        result = null;
        errors = null;
    }

    public AddCommentResponse(int id, boolean result, Map<String, String> errors) {
        this.id = id;
        this.result = result;
        this.errors = errors;
    }

    public AddCommentResponse(Boolean result, Map<String, String> errors) {
        this.result = result;
        this.errors = errors;
        id = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
