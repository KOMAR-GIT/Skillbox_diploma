package main.service;

import main.api.response.AuthCheckResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthCheckService {

    public AuthCheckService() {
    }

    public AuthCheckResponse getAuthCheck() {
        return new AuthCheckResponse(false);
    }

}
