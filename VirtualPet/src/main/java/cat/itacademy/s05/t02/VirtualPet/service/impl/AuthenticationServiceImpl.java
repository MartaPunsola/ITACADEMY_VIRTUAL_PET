package cat.itacademy.s05.t02.VirtualPet.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import cat.itacademy.s05.t02.VirtualPet.model.enums.EnumRole;
import cat.itacademy.s05.t02.VirtualPet.payload.request.SignInRequest;
import cat.itacademy.s05.t02.VirtualPet.payload.request.SignUpRequest;
import cat.itacademy.s05.t02.VirtualPet.payload.response.JwtResponse;
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
    private final AuthenticationManager authenticationManager; //??

    @Override
    public JwtResponse signup(SignUpRequest request) {
        var user = User.builder().username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(EnumRole.ROLE_USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        //validate token???
        return JwtResponse.builder().token(jwt).build();
        //abans de retornar la resposta, he de gestionar el rol assignat al user
        //si el username o l'email ja estan registrats, exception
    }

    @Override
    public JwtResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password.")); //???
        var jwt = jwtService.generateToken(user);
        //validate token???
        return JwtResponse.builder().token(jwt).build();
    }

    //refreshToken??? vid. vídeo minut 28 aprox
}
