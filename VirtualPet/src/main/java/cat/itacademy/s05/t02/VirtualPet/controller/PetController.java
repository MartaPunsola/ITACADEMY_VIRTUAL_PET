package cat.itacademy.s05.t02.VirtualPet.controller;

import cat.itacademy.s05.t02.VirtualPet.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.FindPetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.UpdatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.model.Pet;
import cat.itacademy.s05.t02.VirtualPet.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets") //tots els users tenen permisos / afegir pets?? modificar?
@RequiredArgsConstructor
public class PetController {

    //fer swagger!!
    private final PetService petService;

    //create
    @PostMapping("/new")
    public ResponseEntity<PetDTO> createPet(@RequestBody @Valid CreatePetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(request));
    }

    @GetMapping("/getOne")
    public ResponseEntity<PetDTO> getOnePet(@RequestBody FindPetRequest findPetRequest) {
        return ResponseEntity.ok(petService.getOnePetForUser(findPetRequest));
        //o status?
    }

    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<PetDTO>> getAllMyPets(@PathVariable("id") Long userId) { //o requestBody??
        return ResponseEntity.ok(petService.getAllPetsForUser(userId));
        //o status?
    }

    @GetMapping("/admin/getAllPets") //només podrà accedir-hi l'admin
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
        //o status?
    }

    //un únic endpoint o un diferent per cada modificació?
    @PutMapping("/update") //un altre nom??
    public ResponseEntity<PetDTO> updatePet(@RequestBody UpdatePetRequest request) {
        return ResponseEntity.ok(petService.updatePet(request));
        //o status?
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePet(@RequestBody FindPetRequest findPetRequest) {
        petService.deletePet(findPetRequest);
        return ResponseEntity.ok().body("Pet successfully deleted.");
    }


}
