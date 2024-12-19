package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.dto.request.SignInRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.SignUpRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.JwtResponse;

public interface AuthenticationService {

    JwtResponse signup(SignUpRequest request);
    JwtResponse signin(SignInRequest request);
}
