package main.service;

import main.api.response.LoginResponse;
import main.dto.UserDTO;
import main.dto.interfaces.UserInterface;
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
        UserInterface currentUser = userRepository.getByEmail(email);
        if(currentUser == null) {
            return new LoginResponse();
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        UserDTO userDTO = modelMapper.map(currentUser, UserDTO.class);
        loginResponse.setUser(userDTO);
        loginResponse.getUser().setSettings(true);
        return loginResponse;
    }

}
