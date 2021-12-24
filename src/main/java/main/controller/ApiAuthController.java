package main.controller;

import main.api.response.AuthCheckResponse;
import main.service.AuthCheckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {

    private final AuthCheckService authCheckService;

    private final ModelMapper modelMapper;

    public ApiAuthController(AuthCheckService authCheckService, ModelMapper modelMapper) {
        this.authCheckService = authCheckService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/auth/check")
    public ResponseEntity<AuthCheckResponse> authCheck() {


        return new ResponseEntity<>(authCheckService.getAuthCheck(), HttpStatus.OK);
    }

}
