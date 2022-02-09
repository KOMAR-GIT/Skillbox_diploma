package main.controller;

import main.api.request.LoginRequest;
import main.api.response.CaptchaResponse;
import main.api.response.LoginResponse;
import main.api.response.ResponseWithErrors;
import main.api.response.SuccessResultResponse;
import main.dto.UserForRegistrationDTO;
import main.security.SecurityUser;
import main.service.AuthCheckService;
import main.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;

@RestController
public class ApiAuthController {

    private final AuthCheckService authCheckService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public ApiAuthController(AuthCheckService authCheckService,
                             AuthenticationManager authenticationManager,
                             UserService userService) {
        this.authCheckService = authCheckService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/api/auth/check")
    public ResponseEntity<LoginResponse> authCheck(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(new LoginResponse(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.getLoginResponse(principal.getName()), HttpStatus.OK);
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
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            return new ResponseEntity<>(userService.getLoginResponse(securityUser.getUsername()), HttpStatus.OK);
        } catch (Exception e) {
            logout();
        }
        return ResponseEntity.ok(new LoginResponse(false, null));
    }

    @GetMapping("/api/auth/logout")
    public ResponseEntity<SuccessResultResponse> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new SuccessResultResponse(true));
    }


}
