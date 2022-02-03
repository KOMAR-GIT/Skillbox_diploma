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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthCheckService {

    @Value("${captcha.lifetimeInMinutes}")
    private int captchaLifetime;
    private final CaptchaCodeRepository captchaCodeRepository;
    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    public AuthCheckService(CaptchaCodeRepository captchaCodeRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.captchaCodeRepository = captchaCodeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Scheduled(fixedDelay = 7_200_000)
    public void deleteOldCaptcha() {
        captchaCodeRepository.deleteOldCaptcha(captchaLifetime);
    }

    public CaptchaResponse generateCaptchaCodes() {
        Cage cage = new GCage();
        String token = cage.getTokenGenerator().next();
        String encodedString = "data:image/png;base64, " + Base64.getEncoder().encodeToString(cage.draw(token));
        String secret = UUID.randomUUID().toString();
        captchaCodeRepository.save(new CaptchaCode(Calendar.getInstance().getTime(), token, secret));
        return new CaptchaResponse(secret, encodedString);
    }

    public boolean checkCaptcha(String secretCode) {
        return captchaCodeRepository.findBySecretCode(secretCode) != null;
    }

    public Map<String, String> addNewUser(UserForRegistrationDTO userDTO) {
        Map<String, String> errors = new HashMap<>();
        isEmailValid(userDTO.getEmail(), errors);
        isNameCorrect(userDTO.getName(), errors);
        isPasswordCorrect(userDTO.getPassword(), errors);
        isCaptchaCorrect(userDTO.getCaptchaSecret(), errors);

        if (errors.isEmpty()) {
            User user = new User(false,
                    Calendar.getInstance().getTime(),
                    userDTO.getName(),
                    userDTO.getEmail(),
                    passwordEncoder.encode(userDTO.getPassword()),
                    userDTO.getCaptcha(),
                    null);
            userRepository.save(user);
            return null;
        }
        return errors;
    }


    private void isEmailValid(String email, Map<String, String> map) {
        if (userRepository.getByEmail(email) != null) {
            map.put("email", ErrorsForRegistration.email);
        }
    }

    private void isNameCorrect(String name, Map<String, String> map) {
        if (!name.chars().allMatch(Character::isLetter)) {
            map.put("name", ErrorsForRegistration.name);
        }
    }

    private void isPasswordCorrect(String password, Map<String, String> map) {
        if (password.length() < 6) {
            map.put("password", ErrorsForRegistration.password);
        }
    }

    private void isCaptchaCorrect(String secret, Map<String, String> map) {
        if (!checkCaptcha(secret)) {
            map.put("captcha", ErrorsForRegistration.captcha);
        }
    }
}
