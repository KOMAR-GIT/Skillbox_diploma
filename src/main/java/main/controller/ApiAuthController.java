package main.controller;

import main.api.response.AuthCheckResponse;
import main.api.response.CaptchaResponse;
import main.api.response.RegisterResponse;
import main.dto.UserForRegistrationDTO;
import main.service.AuthCheckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/auth/captcha")
    public ResponseEntity<CaptchaResponse> captcha(){
        return new ResponseEntity<>(authCheckService.generateCaptchaCodes(), HttpStatus.OK);
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserForRegistrationDTO user){
        RegisterResponse registerResponse = new RegisterResponse(authCheckService.addNewUser(user));
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

}
