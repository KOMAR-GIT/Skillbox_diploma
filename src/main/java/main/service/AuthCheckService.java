package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.response.AuthCheckResponse;
import main.api.response.CaptchaResponse;
import main.dto.ErrorsForRegistration;
import main.dto.UserForRegistrationDTO;
import main.model.CaptchaCode;
import main.model.User;
import main.repository.CaptchaCodeRepository;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AuthCheckService {

    private final CaptchaCodeRepository captchaCodeRepository;
    private final UserRepository userRepository;

    public AuthCheckService(CaptchaCodeRepository captchaCodeRepository, UserRepository userRepository) {
        this.captchaCodeRepository = captchaCodeRepository;
        this.userRepository = userRepository;
    }

    public AuthCheckResponse getAuthCheck() {
        return new AuthCheckResponse(false);
    }

    public CaptchaResponse generateCaptchaCodes() {
        Cage cage = new GCage();
        String token = cage.getTokenGenerator().next();
        String encodedString = "data:image/png;base64, " + Base64.getEncoder().encodeToString(cage.draw(token));
        String secret = UUID.randomUUID().toString();
        captchaCodeRepository.save(new CaptchaCode(Date.valueOf(LocalDate.now()), token, secret));
        return new CaptchaResponse(secret, encodedString);
    }

    public boolean checkCaptcha(String secretCode) {
        return captchaCodeRepository.findBySecretCode(secretCode) != null;
    }

    public Map<String, String> addNewUser(UserForRegistrationDTO userDTO) {
        Map<String, String> errors = new HashMap<>();
        System.out.println(userDTO.getName());
        errors = isEmailValid(userDTO.getEmail(), errors);
        errors = isNameCorrect(userDTO.getName(), errors);
        errors = isPasswordCorrect(userDTO.getPassword(), errors);
        if (!checkCaptcha(userDTO.getCaptchaSecret())) {
            errors.put("captcha", ErrorsForRegistration.captcha);
        }

        if (errors.isEmpty()) {
            User user = new User(false,
                    Date.valueOf(LocalDate.now()),
                    userDTO.getName(),
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    userDTO.getCaptcha(),
                    null);
            userRepository.save(user);
            return null;
        }
        return errors;
    }


    private Map<String, String> isEmailValid(String email, Map<String, String> map) {
        if (userRepository.getByEmail(email) == null) {
            return map;
        }
        map.put("email", ErrorsForRegistration.email);
        return map;
    }

    private Map<String, String> isNameCorrect(String name, Map<String, String> map) {
        if (name.chars().allMatch(Character::isLetter)) {
            return map;
        }
        map.put("name", ErrorsForRegistration.name);
        return map;
    }

    private Map<String, String> isPasswordCorrect(String password, Map<String, String> map) {
        if (password.length() >= 6) {
            return map;
        }
        map.put("password", ErrorsForRegistration.password);
        return map;
    }

}
