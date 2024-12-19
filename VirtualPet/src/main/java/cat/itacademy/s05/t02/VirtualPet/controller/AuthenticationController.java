package cat.itacademy.s05.t02.VirtualPet.controller;

import cat.itacademy.s05.t02.VirtualPet.dto.request.SignInRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.SignUpRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.JwtResponse;
import cat.itacademy.s05.t02.VirtualPet.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    //fer swagger!!

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignUpRequest request) { //valid
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody SignInRequest request) { //valid
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
