package cat.itacademy.s05.t02.VirtualPet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user") //tots els users tenen permisos / afegir pets?? modificar?
@RequiredArgsConstructor
public class PetController {

    //create
    @PostMapping("/new")
    public void newPet() {}



    //read
    /*@GetMapping("/pets")
    public ResponseEntity<List<Pet>> getMyPets(Authentication authentication) { //showAllPets...
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        List<Pet> pets = petRepository.findByOwnerId(user.getId());
        return ResponseEntity.ok(pets);
    }*/

    //un únic endpoint o un diferent per cada modificació?
    @PutMapping("/update") //un altre nom??
    public void update() {}

    @DeleteMapping("/delete")
    public void delete() {}


}
