package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.dto.response.UserDTO;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoUsersException;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    UserDetailsService userDetailsService();
    List<User> allUsers() throws NoUsersException;
    User findUserByEmail(String email);
    User findCurrentUser();
    User findUserById(Long id);
    UserDTO buildUserDTO(User user);

}
