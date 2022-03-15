package main.api.request;

public class RestoreRequest {

    private String email;

    public RestoreRequest() {
    }

    public RestoreRequest(String email) {
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
