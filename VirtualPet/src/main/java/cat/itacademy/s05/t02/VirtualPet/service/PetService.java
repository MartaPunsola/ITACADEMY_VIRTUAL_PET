package cat.itacademy.s05.t02.VirtualPet.service;

import cat.itacademy.s05.t02.VirtualPet.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.FindPetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.UpdatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NameAlreadyExistsException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoPetsException;

import java.util.List;

public interface PetService {

    PetDTO createPet(CreatePetRequest request) throws NameAlreadyExistsException;
    PetDTO getOnePetForUser(String petName);
    List<PetDTO> getAllPetsForUser() throws NoPetsException;
    List<PetDTO> getAllPets() throws NoPetsException;
    PetDTO updatePet(UpdatePetRequest request);
    void deletePet(FindPetRequest findPetRequest);

}
