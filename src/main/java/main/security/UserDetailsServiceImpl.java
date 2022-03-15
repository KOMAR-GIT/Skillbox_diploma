package main.security;

import main.model.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return loadUser(email);
    }

    private SecurityUser loadUser(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        List authorities = new ArrayList();
        authorities.addAll(user.getRole().getAuthorities());
        return new SecurityUser(user.getEmail(),user.getPassword(), user.getId(), authorities);
    }
}
