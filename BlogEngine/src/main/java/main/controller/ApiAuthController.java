package main.controller;

import main.api.response.AuthCheckResponse;
import main.service.AuthCheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {

    @GetMapping("/api/auth/check")
    public AuthCheckResponse authCheck() {
        AuthCheckService authCheckService = new AuthCheckService();
        return authCheckService.getAuthCheck();
    }

}
