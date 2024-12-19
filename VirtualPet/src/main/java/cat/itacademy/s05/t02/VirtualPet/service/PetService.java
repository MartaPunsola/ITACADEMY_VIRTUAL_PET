package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.FindPetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.model.Pet;

import java.util.List;

public interface PetService {

    //createPet
    PetDTO createPet(CreatePetRequest request);
    //getOnePet
    //getUserPets
    //getAllPets
    List<PetDTO> getAllPets();
    //updatePet
    //deletePet
    void deletePet(FindPetRequest findPetRequest); //o name???


}
