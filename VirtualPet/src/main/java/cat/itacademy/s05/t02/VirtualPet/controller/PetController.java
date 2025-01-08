package cat.itacademy.s05.t02.VirtualPet.controller;

import cat.itacademy.s05.t02.VirtualPet.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.FindPetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.UpdatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {

    //fer swagger!!
    private final PetService petService;


    @PostMapping("/new")
    public ResponseEntity<PetDTO> createPet(@RequestBody @Valid CreatePetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(request));
    }

    @GetMapping("/getOne")
    public ResponseEntity<PetDTO> getOnePet(@RequestParam String name) {
        return ResponseEntity.ok(petService.getOnePetForUser(name));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PetDTO>> getAllMyPets() {
        return ResponseEntity.ok(petService.getAllPetsForUser());
    }

    @GetMapping("/admin/getAllPets")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @PutMapping("/update")
    public ResponseEntity<PetDTO> updatePet(@RequestBody UpdatePetRequest request) {
        return ResponseEntity.ok(petService.updatePet(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePet(@RequestBody FindPetRequest findPetRequest) {
        petService.deletePet(findPetRequest);
        return ResponseEntity.ok().body("Pet successfully deleted.");
    }


}
