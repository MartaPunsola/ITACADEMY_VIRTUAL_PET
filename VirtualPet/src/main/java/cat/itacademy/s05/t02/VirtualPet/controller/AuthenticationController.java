package cat.itacademy.s05.t02.VirtualPet.controller;

import cat.itacademy.s05.t02.VirtualPet.dto.request.SignInRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.SignUpRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.JwtResponse;
import cat.itacademy.s05.t02.VirtualPet.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody @Valid SignUpRequest request) {
        log.info("Received signup request for email: {}", request.getEmail());
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody @Valid SignInRequest request) {
        log.info("Received signin request for email: {}", request.getEmail());
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
