package main.controller;

import main.api.request.*;
import main.api.response.*;
import main.model.enums.GlobalSettingsCodes;
import main.dto.UserForRegistrationDto;
import main.dto.interfaces.StatisticsInterface;
import main.security.SecurityUser;
import main.service.AuthCheckService;
import main.service.SettingsService;
import main.service.StatisticsService;
import main.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.Objects;

@RestController
public class ApiAuthController {

    private final AuthCheckService authCheckService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final StatisticsService statisticsService;
    private final SettingsService settingsService;
    private final ModelMapper modelMapper;

    public ApiAuthController(AuthCheckService authCheckService,
                             AuthenticationManager authenticationManager,
                             UserService userService,
                             StatisticsService statisticsService,
                             SettingsService settingsService,
                             ModelMapper modelMapper) {
        this.authCheckService = authCheckService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.statisticsService = statisticsService;
        this.settingsService = settingsService;
        this.modelMapper = modelMapper;
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
    public ResponseEntity<ResponseWithErrors> register(@RequestBody UserForRegistrationDto user) {
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
            return ResponseEntity.ok(new LoginResponse(false, null));

        }
    }

    @GetMapping(value = "/api/auth/logout")
    public ResponseEntity<SuccessResultResponse> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new SuccessResultResponse(true));
    }

    @PostMapping(value = "/api/profile/my", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseWithErrors> editProfileWithPhoto(
            @ModelAttribute EditProfileRequest editProfileRequest) throws IOException {
        SecurityUser securityUser = (SecurityUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(authCheckService
                .editProfile(securityUser.getUserId(), editProfileRequest));
    }


    @PostMapping(value = "/api/profile/my", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseWithErrors> editProfile(
            @RequestBody EditProfileWithoutPhotoRequest editProfileRequest) {
        SecurityUser securityUser = (SecurityUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(authCheckService.editProfile(
                securityUser.getUserId(),
                editProfileRequest));
    }

    @GetMapping("/api/statistics/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<StatisticResponse> userStatistics() {
        StatisticResponse statisticResponse = statisticsService.getUserStatistics();
        return ResponseEntity.ok(statisticResponse);
    }

    @GetMapping("/api/statistics/all")
    public ResponseEntity<StatisticResponse> globalStatistics() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = auth.getName().equals("anonymousUser")
                ? null : (SecurityUser) auth.getPrincipal();
        if (settingsService.getOneSetting(GlobalSettingsCodes.STATISTICS_IS_PUBLIC) ||
                Objects.requireNonNull(securityUser).getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("user:moderate"))) {
            StatisticsInterface statisticsInterface = statisticsService.getGlobalStatistics();
            StatisticResponse statisticResponse = modelMapper.map(statisticsInterface, StatisticResponse.class);
            statisticResponse.setFirstPublication(statisticResponse.getFirstPublication() / 1000);
            return ResponseEntity.ok(statisticResponse);
        }
        return ResponseEntity.status(401).body(null);
    }

    @PostMapping("/api/auth/restore")
    public ResponseEntity<?> restorePassword(@RequestBody RestoreRequest restoreRequest, HttpServletRequest request) throws MalformedURLException {
        return ResponseEntity.ok(authCheckService.restore(restoreRequest.getEmail(), request));
    }

    @PostMapping("/api/auth/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(authCheckService.changePassword(changePasswordRequest));
    }

    @PutMapping("/api/settings")
    public ResponseEntity<?> saveSettings(@RequestBody SettingsRequest settingsRequest) {
        return settingsService.saveSettings(settingsRequest) ? ResponseEntity.status(200).build()
                : ResponseEntity.status(401).build();
    }

}
