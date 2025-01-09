package cat.itacademy.s05.t02.VirtualPet.service.impl;

import cat.itacademy.s05.t02.VirtualPet.exception.custom.NameAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import cat.itacademy.s05.t02.VirtualPet.model.enums.Role;
import cat.itacademy.s05.t02.VirtualPet.dto.request.SignInRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.SignUpRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.JwtResponse;
import cat.itacademy.s05.t02.VirtualPet.repository.UserRepository;
import cat.itacademy.s05.t02.VirtualPet.service.AuthenticationService;
import cat.itacademy.s05.t02.VirtualPet.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse signup(SignUpRequest request) {

        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new NameAlreadyExistsException("This username is not available.");
        }
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("This email address is already registered.");
        }

        var user = User.builder().username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);

        return JwtResponse.builder().token(jwt).build();
    }

    @Override
    public JwtResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);

        return JwtResponse.builder().token(jwt).build();
    }

}
