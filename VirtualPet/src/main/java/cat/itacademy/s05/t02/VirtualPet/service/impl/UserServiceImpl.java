package cat.itacademy.s05.t02.VirtualPet.service.impl;

import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoUsersException;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import cat.itacademy.s05.t02.VirtualPet.repository.UserRepository;
import cat.itacademy.s05.t02.VirtualPet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found."));

                /*User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found."));
                //cal aplicar o ja està implícit a algun lloc?
                // Mapeja els rols del teu usuari al tipus GrantedAuthority
                Collection<GrantedAuthority> authorities = user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .toList();

                return new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        authorities
                );*/
            }
        };
    }

    //mètode find All; cal??? no el demana l'enunciat!
    @Override
    public List<User> allUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new NoUsersException("There are no registered users at the moment.");
        }
        return users;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
