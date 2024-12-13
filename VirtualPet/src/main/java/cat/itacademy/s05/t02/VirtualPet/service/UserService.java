package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {

    //UserDetailsService userDetailsService();
    //UserDetails loadUserByEmail(String username);
    Optional<User> findUserByEmail(String email);
}
