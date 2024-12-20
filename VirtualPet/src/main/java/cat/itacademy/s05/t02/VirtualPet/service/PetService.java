package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.FindPetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.UpdatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoPetsException;
import cat.itacademy.s05.t02.VirtualPet.model.Pet;

import java.util.List;

public interface PetService {

    //posar aquí els throws als mètodes que llancen throw new????

    //createPet
    PetDTO createPet(CreatePetRequest request);
    //buildDTO
    PetDTO buildPetDTO(Pet pet);
    //getOnePet
    PetDTO getOnePetForUser(FindPetRequest findPetRequest);
    //getUserPets
    List<PetDTO> getAllPetsForUser(Long userId);
    //getAllPets
    List<PetDTO> getAllPets();
    //updatePet
    PetDTO updatePet(UpdatePetRequest request);
    //deletePet
    void deletePet(FindPetRequest findPetRequest); //o name???


}
