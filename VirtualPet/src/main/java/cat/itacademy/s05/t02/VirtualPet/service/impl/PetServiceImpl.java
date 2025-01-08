package cat.itacademy.s05.t02.VirtualPet.service.impl;

import cat.itacademy.s05.t02.VirtualPet.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.FindPetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.request.UpdatePetRequest;
import cat.itacademy.s05.t02.VirtualPet.dto.response.PetDTO;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NameAlreadyExistsException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.NoPetsException;
import cat.itacademy.s05.t02.VirtualPet.exception.custom.PetNotFoundException;
import cat.itacademy.s05.t02.VirtualPet.model.Pet;
import cat.itacademy.s05.t02.VirtualPet.model.User;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetAccessory;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetInteraction;
import cat.itacademy.s05.t02.VirtualPet.model.enums.PetLocation;
import cat.itacademy.s05.t02.VirtualPet.repository.PetRepository;
import cat.itacademy.s05.t02.VirtualPet.repository.UserRepository;
import cat.itacademy.s05.t02.VirtualPet.service.PetInteractionService;
import cat.itacademy.s05.t02.VirtualPet.service.PetService;
import cat.itacademy.s05.t02.VirtualPet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PetInteractionService petInteractionService;

    //chatgpt user logejat: opció per buscar el user que està logejat ara mateix
    //provar quan tingui el front; si no, es queda iqual
    /*
    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User currentUser = userService.findUserByEmail(currentUserEmail);
}*/

    @Override
    public PetDTO createPet(CreatePetRequest request) {
        //User currentUser = userService.findUserById(request.getUserId());
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findCurrentUser();

        if(petRepository.findByUserIdAndName(currentUser.getId(), request.getName()).isPresent()) {
            throw new NameAlreadyExistsException("This name is not available.");
        }

        Pet newPet = buildPet(request, currentUser);
        //si guardo el per al mètode buildPet, cal que el posi al user o es fa directaemnt??
        currentUser.getPets().add(newPet);
        userRepository.save(currentUser); //es pot fer un mètode al userService per no haver de posar el repo aquí: val la pena??

        return buildPetDTO(newPet);
    }

    private Pet buildPet(CreatePetRequest request, User user) {
        return Pet.builder()
                .name(request.getName())
                .type(request.getType())
                .color(request.getColor())
                .happiness(40)
                .energyLevel(50)
                .hunger(60)
                .accessories(new HashSet<>())
                .location(PetLocation.HOME)
                .user(user)
                .build();
    }

    //readOne (NO OBLIGATORI SEGONS L'ENUNCIAT)
    //buscar per id i mostrar totes les dades
    @Override
    public PetDTO getOnePetForUser(String petName) {
        Pet foundPet = findPet(petName);
        return buildPetDTO(foundPet);
    }

    //readAllForUser
    @Override
    public List<PetDTO> getAllPetsForUser() {
        User currentUser = userService.findCurrentUser();
        if (currentUser.getPets() == null || currentUser.getPets().isEmpty()) {
            throw new NoPetsException("There are no pets to show.");
        }

        return currentUser.getPets()
                .stream()
                .map(this::buildPetDTO)
                .collect(Collectors.toList());
    }

    //tots els pets de tots els users
    @Override
    public List<PetDTO> getAllPets() {
        List<User> users = userService.allUsers();
        List<PetDTO> pets = users.stream()
                .flatMap(user -> user.getPets().stream())
                .map(this::buildPetDTO)
                .collect(Collectors.toList());

        if (pets.isEmpty()) {
            throw new NoPetsException("No pets found in the system.");
        }

        return pets;
    }

    @Override //crec que el puc tornar a fer private i treure'l de la interface
    public PetDTO buildPetDTO(Pet pet) {
        return PetDTO.builder()
                .username(pet.getUser().getUsername())
                .name(pet.getName())
                .color(pet.getColor())
                .type(pet.getType())
                .happiness(pet.getHappiness())
                .energyLevel(pet.getEnergyLevel())
                .hunger(pet.getHunger())
                .isAsleep(pet.isAsleep())
                .location(pet.getLocation())
                .accessories(pet.getAccessories())
                .build();
        //el JSON object no em retorna l'id, diu que és null
    }

    @Override
    @Transactional //??
    public PetDTO updatePet(UpdatePetRequest request) {
        Pet foundPet = findPet(request.getName());

        if(foundPet.getLocation() != request.getLocation()) {
            petInteractionService.changeLocation(foundPet, request.getLocation());
        }
        if(request.getAccessory() != null) {
           petInteractionService.changeAccessories(foundPet, request.getAccessory());
           log.info("Accessories after update: {}", foundPet.getAccessories());
        }
        if(request.getPetInteraction() != PetInteraction.NONE) { //?? cal none?? puc fer que el camp sigui null?
            petInteractionService.interact(foundPet, request.getPetInteraction());
        }

        //guardar els canvis al repositori
        //foundPet.getAccessories().clear();
        //foundPet.getAccessories().addAll(foundPet.getAccessories());
        petRepository.save(foundPet);
        return buildPetDTO(foundPet);
    }

    @Override
    public void deletePet(FindPetRequest findPetRequest) { //o per name?
        Pet foundPet = findPet(findPetRequest.getName());
        petRepository.delete(foundPet);
    }

    private Pet findPet(String petName) {
        User currentUser = userService.findCurrentUser();
        return petRepository.findByUserIdAndName(currentUser.getId(), petName)
                .orElseThrow(() -> new PetNotFoundException("You have no pets under this name."));
    }


}
