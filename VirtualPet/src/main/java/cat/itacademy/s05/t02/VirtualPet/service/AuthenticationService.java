package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.dto.request.SignInRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.SignUpRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.JwtResponse;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NameAlreadyExistsException;

public interface AuthenticationService {

    JwtResponse signup(SignUpRequest request) throws NameAlreadyExistsException, IllegalArgumentException;
    JwtResponse signin(SignInRequest request);
}
