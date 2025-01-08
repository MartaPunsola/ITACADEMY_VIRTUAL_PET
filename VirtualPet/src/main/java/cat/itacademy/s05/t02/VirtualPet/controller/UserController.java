package cat.itacademy.s05.t02.VirtualPet.controller;

import cat.itacademy.s05.t02.VirtualPet.dto.response.UserDTO;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import cat.itacademy.s05.t02.VirtualPet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //fer swagger!!

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users.stream().map(userService::buildUserDTO).collect(Collectors.toList()));
    }
}
