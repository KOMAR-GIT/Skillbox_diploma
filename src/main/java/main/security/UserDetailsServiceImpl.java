package main.security;

import com.fasterxml.jackson.core.io.JsonEOFException;
import main.model.User;
import main.repository.UserRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
        try {
            return loadUser(email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SecurityUser loadUser(String email) throws UsernameNotFoundException, JSONException{
        User user = userRepository.getByEmail(email);
        List authorities = new ArrayList();
        authorities.addAll(user.getRole().getAuthorities());
        return new SecurityUser(user.getEmail(),user.getPassword(), user.getId(), authorities);
    }
}
