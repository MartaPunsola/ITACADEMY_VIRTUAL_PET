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
@RequestMapping("/api/v1/admin") //només l'admin hi podrà accedir
@RequiredArgsConstructor
public class UserController { //canviar nom a AdminController??

    private final UserService userService;

    //fer swagger!!

    /*@GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }*/

    //veure tots els users

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users.stream().map(userService::buildUserDTO).collect(Collectors.toList()));
    }
}
