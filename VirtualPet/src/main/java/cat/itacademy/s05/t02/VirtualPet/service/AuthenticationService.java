package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.payload.request.SignInRequest;
import cat.itacademy.s05.t02.VirtualPet.payload.request.SignUpRequest;
import cat.itacademy.s05.t02.VirtualPet.payload.response.JwtResponse;

public interface AuthenticationService {

    JwtResponse signup(SignUpRequest request);
    JwtResponse signin(SignInRequest request);
}
