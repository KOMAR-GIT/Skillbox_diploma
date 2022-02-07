package main.service;

import main.api.response.LoginResponse;
import main.dto.UserDTO;
import main.model.User;
import main.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public LoginResponse getLoginResponse(String email){
        main.model.User currentUser =
                userRepository.getByEmail(email);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUser(modelMapper.map(currentUser, UserDTO.class));
        return loginResponse;
    }

}
