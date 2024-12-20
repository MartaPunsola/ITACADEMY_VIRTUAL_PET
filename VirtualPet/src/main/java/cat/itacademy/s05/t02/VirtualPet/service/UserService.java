package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoUsersException;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //posar aquí els throws als mètodes que llancen throw new????

    UserDetailsService userDetailsService();
    List<User> allUsers() throws NoUsersException;
    //UserDetails loadUserByEmail(String username);
    User findUserByEmail(String email);
    User findUserById(Long id);

}
