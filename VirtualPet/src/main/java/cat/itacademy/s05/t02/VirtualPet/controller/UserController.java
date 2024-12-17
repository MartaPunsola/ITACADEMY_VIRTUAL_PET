package cat.itacademy.s05.t02.VirtualPet.controller;

import cat.itacademy.s05.t02.VirtualPet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin") //només l'admin hi podrà accedir
@RequiredArgsConstructor
public class UserController { //canviar nom a AdminController??

    private final UserService userService;

    /*@GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }*/

    //veure tots els users
    //@PreAuthorize("hasRole('ADMIN')") //crec que ja no cal
    /*@GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }*/
}
