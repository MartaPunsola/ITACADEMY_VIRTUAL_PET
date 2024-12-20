package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.model.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetInteractionService {

    //atributs??
    private final PetService petService;

    //addAccessory
    public PetDTO addAccessory(Pet pet) {
        //comprovar que no el té i afegir
        pet.setHappiness(increaseState(pet.getHappiness())); //augmentar 20%
        return petService.buildPetDTO(pet);
    }

    //removeAccessory
    public PetDTO removeAccessory(Pet pet) {

        pet.setHappiness(decreaseState(pet.getHappiness())); //disminuir 20% happiness
        return petService.buildPetDTO(pet);
    }

    //changeLocation
    public PetDTO changeLocation(Pet pet) {
        return petService.buildPetDTO(pet);
    }

    //interact
    public PetDTO interact(Pet pet) {
        return petService.buildPetDTO(pet);
    }

    //private feed

    //private sleep

    //private play

    private int increaseState(int state) {
        return (int) (state * 1.2);
    }

    private int decreaseState(int state) {
        return (int) (state / 1.2); //?????
    }
}
