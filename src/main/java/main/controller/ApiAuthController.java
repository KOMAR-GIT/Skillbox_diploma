package main.controller;

import main.api.request.LoginRequest;
import main.api.response.CaptchaResponse;
import main.api.response.LoginResponse;
import main.api.response.SuccessResultResponse;
import main.api.response.ResponseWithErrors;
import main.dto.UserDTO;
import main.dto.UserForRegistrationDTO;
import main.repository.UserRepository;
import main.security.SecurityUser;
import main.service.AuthCheckService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ApiAuthController {

    private final AuthCheckService authCheckService;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    public ApiAuthController(AuthCheckService authCheckService, ModelMapper modelMapper,
                             AuthenticationManager authenticationManager,
                             UserRepository userRepository) {
        this.authCheckService = authCheckService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/auth/check")
    public ResponseEntity<LoginResponse> authCheck(Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(new LoginResponse(), HttpStatus.OK);
        }
        return new ResponseEntity<>(getLoginResponse(principal.getName()), HttpStatus.OK);
    }


    @GetMapping("/api/auth/captcha")
    public ResponseEntity<CaptchaResponse> captcha() {
        return new ResponseEntity<>(authCheckService.generateCaptchaCodes(), HttpStatus.OK);
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<ResponseWithErrors> register(@RequestBody UserForRegistrationDTO user) {
        ResponseWithErrors responseWithErrors = authCheckService.addNewUser(user);
        return new ResponseEntity<>(responseWithErrors, HttpStatus.OK);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return new ResponseEntity<>(getLoginResponse(securityUser.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/api/auth/logout")
    public ResponseEntity<SuccessResultResponse> logout(){
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new SuccessResultResponse(true));
    }

    private LoginResponse getLoginResponse(String email) {
        main.model.User currentUser =
                userRepository.getByEmail(email);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUser(modelMapper.map(currentUser, UserDTO.class));
        return loginResponse;
    }


}
